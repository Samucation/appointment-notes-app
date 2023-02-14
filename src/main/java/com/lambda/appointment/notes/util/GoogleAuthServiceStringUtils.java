package com.lambda.appointment.notes.util;

import com.lambda.appointment.notes.config.GoogleAuthConfig;
import com.lambda.appointment.notes.dto.response.CodeExtractedFromGoogleUrlResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URLDecoder;

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

    public String extractCodeIntoUrlGoogleAuth2Response(String urlWithCode) throws Exception {
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
        } catch (Exception ex) {
            throw new Exception("Error after try get code into url google auth2 response: exception: ", ex);
        }
        return extractedCode;
    }

}
