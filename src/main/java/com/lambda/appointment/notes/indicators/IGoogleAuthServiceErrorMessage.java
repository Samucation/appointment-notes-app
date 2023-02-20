package com.lambda.appointment.notes.indicators;

public interface IGoogleAuthServiceErrorMessage {

   String redirectToGoogleAuthError(RuntimeException ex);

   String isTokenValidError(Exception ex, String accessToken);

   String extractCodeFromRedirectUrlError(Exception ex, String urlWithCode);

   String renewTokenError(RuntimeException ex, String accessToken);

   String exchangeCodeForTokenError(Exception ex, String loginAuthenticateCode);

   String getGoogleUserIdError(RuntimeException ex, String accessToken);

   String getGoogleAuthUrl(Exception e) ;

}
