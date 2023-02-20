package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.dto.response.*;
import com.lambda.appointment.notes.indicators.GoogleAuthServiceErrorMessage;
import com.lambda.appointment.notes.service.GoogleAuthService;
import com.lambda.appointment.notes.service.LoginService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;


@ApplicationScoped
public class GoogleAuthResponseGenerator {

    @Inject
    GoogleAuthService googleAuthService;

    @Inject
    LoginService loginService;

    public GetGoogleAuthUrlResponse getGoogleAuthUrl() {
        try {
            String urlGoogleAuth = googleAuthService.getGoogleAuthUrl();
            return new GetGoogleAuthUrlResponse(urlGoogleAuth);
        } catch (Exception e) {
            String response = GoogleAuthServiceErrorMessage.EXTERNAL.getGoogleAuthUrl(e);
            return new GetGoogleAuthUrlResponse(null, response);
        }
    }

    public ExchangeGoogleCodeForTokenResponse exchangeCodeForToken(String loginAuthenticateCode, Boolean requestRefreshToken) {
        try{
           String accessToken = googleAuthService.exchangeCodeForToken(loginAuthenticateCode, requestRefreshToken);
           return new ExchangeGoogleCodeForTokenResponse(accessToken);
        } catch (Exception ex) {
            return new ExchangeGoogleCodeForTokenResponse(null,
                    GoogleAuthServiceErrorMessage.EXTERNAL.exchangeCodeForTokenError(ex, loginAuthenticateCode));
        }
    }

    public GoogleUserIdResponse getGoogleUserParams(String accessToken) {
        try{
           UserDTO userDTO = googleAuthService.getGoogleUserParams(accessToken);
           return new GoogleUserIdResponse(userDTO);
        } catch (RuntimeException ex) {
            return new GoogleUserIdResponse(GoogleAuthServiceErrorMessage.EXTERNAL.getGoogleUserIdError(ex,accessToken));
        }
    }

    public IsGoogleTokenValidResponse isTokenValid(String accessToken) {
        IsGoogleTokenValidResponse isGoogleTokenValidResponse = new IsGoogleTokenValidResponse();
        try {
            isGoogleTokenValidResponse.setIsTokenValid(googleAuthService.isTokenValid(accessToken));
        } catch (Exception e) {
            isGoogleTokenValidResponse.setIsTokenValid(Boolean.FALSE);
            isGoogleTokenValidResponse.setExternalError(GoogleAuthServiceErrorMessage.EXTERNAL.isTokenValidError(e, accessToken));
        }
        return isGoogleTokenValidResponse;
    }

    public CodeExtractedFromGoogleUrlResponse extractCodeFromRedirectUrl(String urlWithCode) {
        CodeExtractedFromGoogleUrlResponse codeExtractedFromGoogleUrlResponse = new CodeExtractedFromGoogleUrlResponse();
        String extractedCode = null;
        try {
            extractedCode = googleAuthService.extractCodeFromRedirectUrl(urlWithCode);
        } catch (Exception e) {
            codeExtractedFromGoogleUrlResponse.setExternalError(
                    GoogleAuthServiceErrorMessage.EXTERNAL.extractCodeFromRedirectUrlError(e, urlWithCode));
        }
        codeExtractedFromGoogleUrlResponse.setCodeExtractedFromUrl(extractedCode);
        return codeExtractedFromGoogleUrlResponse;
    }

    public Map<String, Object> loginGoogleAccess(String loginAuthenticateCode, Boolean requestRefreshToken) {
        Map<String, Object> loginGoogleAccessResponse = null;
        try {
            loginGoogleAccessResponse = loginService.loginGoogleAccess(loginAuthenticateCode, requestRefreshToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginGoogleAccessResponse;
    }

}
