package com.lambda.appointment.notes.util;

public class GoogleAuthServiceStringUtils {

    public static String createTokenUrlRequest(String loginAuthenticateCode, String clientId, String clientSecret, String redirectUri) {
        String body = "code=" + loginAuthenticateCode + "&client_id=" + clientId + "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri + "&grant_type=authorization_code";
        return body;
    }

}
