package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.dto.*;
import com.lambda.appointment.notes.indicators.GoogleAuthServiceErrorMessage;
import com.lambda.appointment.notes.util.GoogleAuthServiceStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

@ApplicationScoped
public class GoogleAuthService {

    private final String clientId = "119876353782-5lu09i7mfu1omm23r6s9so1i9m5hiejg.apps.googleusercontent.com"; // client ID of Google
    private final String clientSecret = "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"; // client secret of Google
    private final String redirectUri = "https://9a13-191-221-193-141.sa.ngrok.io/"; // Application public URL


    public String getGoogleAuthUrl() {
        String url = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid%20email%20profile" +
                "&prompt=select_account";
        return url;
    }

    public ExchangeGoogleCodeForTokenResponse exchangeCodeForToken(String loginAuthenticateCode) {
        try{
            String body = GoogleAuthServiceStringUtils.createTokenUrlRequest(loginAuthenticateCode, clientId, clientSecret, redirectUri);
            String headerName = "Content-Type";
            String headerValue = "application/x-www-form-urlencoded";
            String requestUrl = "https://oauth2.googleapis.com/token";
            String jsonParameter = "access_token";

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(requestUrl);
            Response response = target.request().header(headerName, headerValue)
                    .post(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            String responseBody = response.readEntity(String.class);
            JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

            String accessToken = responseJson.getString(jsonParameter);

            return new ExchangeGoogleCodeForTokenResponse(accessToken);
        } catch (RuntimeException ex) {
            return new ExchangeGoogleCodeForTokenResponse(null,
                    GoogleAuthServiceErrorMessage.EXTERNAL.exchangeCodeForTokenError(ex, loginAuthenticateCode));
        }
    }

    public GoogleUserIdResponse getGoogleUserId(String accessToken) {
        try{
            String urlGoogleUserInfo = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(urlGoogleUserInfo);
            Response response = target.request().get();

            String responseBody = response.readEntity(String.class);
            JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

            UserDTO userDTO = new UserDTO();
            userDTO.setGoogleUserId(responseJson.getString("id"));
            userDTO.setName(responseJson.getString("name"));
            userDTO.setEmail(responseJson.getString("email"));
            return new GoogleUserIdResponse(userDTO);
        } catch (RuntimeException ex) {
            return new GoogleUserIdResponse(GoogleAuthServiceErrorMessage.EXTERNAL.getGoogleUserIdError(ex,accessToken));
        }
    }

    public IsGoogleTokenValidResponse isTokenValid(String accessToken) {
        IsGoogleTokenValidResponse isGoogleTokenValidResponse = new IsGoogleTokenValidResponse();
        try {
            String urlGoogleTokenInfo = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + accessToken;

            URL url = new URL(urlGoogleTokenInfo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                isGoogleTokenValidResponse.setIsTokenValid(Boolean.TRUE);
            }
            conn.disconnect();
        } catch (Exception e) {
           isGoogleTokenValidResponse.setIsTokenValid(Boolean.FALSE);
           isGoogleTokenValidResponse.setExternalError(GoogleAuthServiceErrorMessage.EXTERNAL.isTokenValidError(e, accessToken));
        }
        return isGoogleTokenValidResponse;
    }

    public RenewGoogleTokenResponse renewToken(String accessToken) {
        try {
            if (isTokenValid(accessToken).getIsTokenValid()) {
                return new RenewGoogleTokenResponse(Boolean.TRUE, accessToken);
            }
            String redirectUrl = getGoogleAuthUrl();
            CodeExtractedFromGoogleUrlResponse codeExtractedFromGoogleUrlResponse = extractCodeFromRedirectUrl(redirectUrl);
            String codeExtracted = codeExtractedFromGoogleUrlResponse.getCodeExtractedFromUrl();

            accessToken = exchangeCodeForToken(codeExtracted).getAccessToken();
            return new RenewGoogleTokenResponse(Boolean.TRUE, accessToken);
        } catch (RuntimeException ex) {
            return new RenewGoogleTokenResponse(Boolean.FALSE,
                    GoogleAuthServiceErrorMessage.EXTERNAL.renewTokenError(ex, accessToken), null);
        }
    }

    public CodeExtractedFromGoogleUrlResponse extractCodeFromRedirectUrl(String urlWithCode) {
        CodeExtractedFromGoogleUrlResponse codeExtractedFromGoogleUrlResponse = new CodeExtractedFromGoogleUrlResponse();
        String extractedCode = null;
        String codeUrlTag = "code=";
        String encodeType = "UTF-8";
        String finalReferenceParam = "&";

        try {
            String decodedUrl = URLDecoder.decode(urlWithCode, encodeType);
            int codeIndex = decodedUrl.indexOf(codeUrlTag);
            if (codeIndex == -1) {
                extractedCode = "";
            }
            int ampersandIndex = decodedUrl.indexOf(finalReferenceParam, codeIndex);
            if (ampersandIndex == -1) {
                extractedCode = decodedUrl.substring(codeIndex + codeUrlTag.length());
            }
            extractedCode = decodedUrl.substring(codeIndex + codeUrlTag.length(), ampersandIndex);
        } catch (Exception e) {
            codeExtractedFromGoogleUrlResponse.setExternalError(
                    GoogleAuthServiceErrorMessage.EXTERNAL.extractCodeFromRedirectUrlError(e, urlWithCode));
        }
        codeExtractedFromGoogleUrlResponse.setCodeExtractedFromUrl(extractedCode);
        return codeExtractedFromGoogleUrlResponse;
    }

}

