package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.dto.UserDTO;

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

@ApplicationScoped
public class GoogleAuthService {

    private final String clientId = "119876353782-5lu09i7mfu1omm23r6s9so1i9m5hiejg.apps.googleusercontent.com"; // Adicionar o client ID fornecido pelo GoogleØ
    private final String clientSecret = "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"; // Adicionar o client secret fornecido pelo Google
    private final String redirectUri = "https://65d4-2804-d55-47f0-c00-3822-22-1977-1bc8.sa.ngrok.io"; // Adicionar a URL de redirecionamento

    public String getGoogleAuthUrl() {
        // Gerar o URL de autenticação do Google
        String url = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid%20email%20profile" +
                "&prompt=select_account";
        return url;
    }

    public String exchangeCodeForToken(String code) {
        // Endpoint de token de acesso do Google
        String body = "code=" + code + "&client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri + "&grant_type=authorization_code";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://oauth2.googleapis.com/token");
        Response response = target.request().header("Content-Type", "application/x-www-form-urlencoded")
                .post(Entity.entity(body, MediaType.APPLICATION_FORM_URLENCODED_TYPE));


        // Analisar a resposta e extrair o token de acesso
        String responseBody = response.readEntity(String.class);

        // Converter a resposta para um objeto JSON
        JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

        // Recuperar o token de acesso
        String accessToken = responseJson.getString("access_token");
        return accessToken;
    }

    public UserDTO getGoogleUserId(String accessToken) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);
        Response response = target.request().get();

        String responseBody = response.readEntity(String.class);
        JsonObject responseJson = Json.createReader(new StringReader(responseBody)).readObject();

        UserDTO userDTO = new UserDTO();
        userDTO.setGoogleUserId(responseJson.getString("id"));
        userDTO.setName(responseJson.getString("name"));
        userDTO.setEmail(responseJson.getString("email"));
        return userDTO;
    }

}

