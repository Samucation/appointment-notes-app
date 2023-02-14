package com.lambda.appointment.notes.util;

import com.lambda.appointment.notes.config.GoogleAuthConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleAuthServiceStringUtils {

    @Inject
    GoogleAuthConfig googleAuthConfig;

    public String createTokenUrlRequest(String loginAuthenticateCode, String clientId, String clientSecret, String redirectUri) {
        String googleTokenUrlRequest = googleAuthConfig.getGoogleTokenUrlRequest();
        String tokenResponse = googleTokenUrlRequest.
                replace("{loginAuthenticateCode}", loginAuthenticateCode).
                replace("{clientId}", clientId).
                replace("{clientSecret}", clientSecret).
                replace("{redirectUri}", redirectUri);
        return tokenResponse;
    }

    public String createGoogleApplicationUrl(String clientId, String redirectUri){
        String applicationUrl =  googleAuthConfig.getGoogleAuthApplicationUrl();
        String url = applicationUrl.
                replace("{clientId}", clientId).
                replace("{redirectUri}", redirectUri);
        return url;
    }

}
