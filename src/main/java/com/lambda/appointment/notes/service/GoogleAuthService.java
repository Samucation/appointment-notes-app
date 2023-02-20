package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.config.GoogleAuthConfig;
import com.lambda.appointment.notes.dto.UserDTO;
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

@ApplicationScoped
public class GoogleAuthService {

    @Inject
    GoogleAuthConfig googleAuthConfig;

    @Inject
    GoogleAuthServiceStringUtils googleAuthServiceStringUtils;


    public String getGoogleAuthUrl() throws Exception {
        try {
            String clientId = googleAuthConfig.getClientId();
            String redirectUri = googleAuthConfig.getRedirectUri();
            String url = googleAuthServiceStringUtils.createGoogleApplicationUrl(clientId, redirectUri);
            return url;
        } catch (Exception e) {
            throw new Exception("Error while get google auth url: that error is: ", e);
        }
    }

    public String exchangeCodeForToken(String loginAuthenticateCode, boolean requestRefreshToken) throws Exception {
        try {
            String clientId = googleAuthConfig.getClientId();
            String clientSecret = googleAuthConfig.getClientSecret();
            String redirectUri = googleAuthConfig.getRedirectUri();

            String bodyBase = googleAuthServiceStringUtils.createTokenUrlRequest(loginAuthenticateCode, clientId, clientSecret, redirectUri);
            String headerName = googleAuthConfig.getHeaderName();
            String headerValue = googleAuthConfig.getHeaderValue();
            String requestUrl = googleAuthConfig.getRequestUrl();
            String jsonAccessTokenParameter = googleAuthConfig.getJsonAccessTokenParameter();
            //String grantType = requestRefreshToken ? "refresh_token" : "authorization_code"; // TODO não está aceitando a tag refresh_token verificar se precisa mesmo para obter o refresh token infinito em teoria só colocar "&access_type=offline" deve resolver
            String grantType = "authorization_code"; // TODO Verificar se precisa mesmo ter o atributo comentado acima que altera entre "refresh_token" que não funciona, e "authorization_code" que funciona para os dois.
            String requestBody;

            if (requestRefreshToken) {
                requestBody = bodyBase + grantType + "&access_type=offline&prompt=consent"; //"&access_type=offline";
            } else {
                requestBody = bodyBase + grantType;
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(requestUrl);
            Response response = target.request().header(headerName, headerValue).post(Entity.entity(requestBody, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            String responseBody = response.readEntity(String.class);
            JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

            if (requestRefreshToken) {
                String accessToken = responseJson.getString(jsonAccessTokenParameter); // TODO Vaidar se existe esse campo no body String accessToken = responseJson.getString("refresh_token"); pois não funciona só funciona com o access_token como parametro.
                return accessToken;
            } else {
                String accessToken = responseJson.getString(jsonAccessTokenParameter);
                return accessToken;
            }
        } catch (RuntimeException ex) {
            throw new Exception("Error while exchange code for token: that error is: ", ex);
        }
    }

    public UserDTO getGoogleUserParams(String accessToken) {
        try{
            String urlGoogleUserInfo = googleAuthConfig.getUserInfoUrl() + accessToken;

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(urlGoogleUserInfo);
            Response response = target.request().get();

            String responseBody = response.readEntity(String.class);
            JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

            UserDTO userDTO = new UserDTO();
            userDTO.setGoogleUserId(responseJson.getString("id"));
            userDTO.setName(responseJson.getString("name"));
            userDTO.setEmail(responseJson.getString("email"));
            return userDTO;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error while get google params in auth2 google cloud, that error is: ", ex);
        }
    }

    public Boolean isTokenValid(String accessToken) throws Exception {
        try {
            String urlGoogleTokenInfo = googleAuthConfig.getTokenInfoUrl() + accessToken;

            URL url = new URL(urlGoogleTokenInfo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            conn.disconnect();

            if (responseCode == 200) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            throw new Exception("Error while validate if token is valid, that error is: ", e);
        }
    }

    public String extractCodeFromRedirectUrl(String urlWithCode) throws Exception {
        try {
           return googleAuthServiceStringUtils.extractCodeIntoUrlGoogleAuth2Response(urlWithCode);
        } catch (Exception e) {
            throw new Exception("Error while extract code from redirect url, that error is: ", e);
        }
    }

}

