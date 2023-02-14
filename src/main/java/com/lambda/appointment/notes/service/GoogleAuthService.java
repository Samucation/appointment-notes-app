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

    public String exchangeCodeForToken(String loginAuthenticateCode) throws Exception {
        try{
            String clientId = googleAuthConfig.getClientId();
            String clientSecret = googleAuthConfig.getClientSecret();
            String redirectUri = googleAuthConfig.getRedirectUri();

            String body = googleAuthServiceStringUtils.createTokenUrlRequest(loginAuthenticateCode, clientId, clientSecret, redirectUri);
            String headerName = googleAuthConfig.getHeaderName();
            String headerValue = googleAuthConfig.getHeaderValue();
            String requestUrl = googleAuthConfig.getRequestUrl();
            String jsonParameter = googleAuthConfig.getJsonParameter();

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(requestUrl);
            Response response = target.request().header(headerName, headerValue).post(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            String responseBody = response.readEntity(String.class);
            JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

            String accessToken = responseJson.getString(jsonParameter);

            return accessToken;
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

