package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.config.GoogleAuthConfig;
import com.lambda.appointment.notes.dto.*;
import com.lambda.appointment.notes.indicators.GoogleAuthServiceErrorMessage;
import com.lambda.appointment.notes.util.GoogleAuthServiceStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject
    GoogleAuthConfig googleAuthConfig;

    @Inject
    GoogleAuthServiceStringUtils googleAuthServiceStringUtils;


    public String getGoogleAuthUrl() {
        String clientId = googleAuthConfig.getClientId();
        String redirectUri = googleAuthConfig.getRedirectUri();
        String url = googleAuthServiceStringUtils.createGoogleApplicationUrl(clientId, redirectUri);
        return url;
    }

    public ExchangeGoogleCodeForTokenResponse exchangeCodeForToken(String loginAuthenticateCode) {
        try{
            String clientId = googleAuthConfig.getClientId();
            String clientSecret = googleAuthConfig.getClientSecret();
            String redirectUri = googleAuthConfig.getRedirectUri();

            String body = googleAuthServiceStringUtils.createTokenUrlRequest(loginAuthenticateCode, clientId, clientSecret, redirectUri);
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

    public GoogleUserIdResponse getGoogleUserParams(String accessToken) {
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

