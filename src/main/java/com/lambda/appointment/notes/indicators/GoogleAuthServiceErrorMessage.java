package com.lambda.appointment.notes.indicators;

import com.lambda.appointment.notes.service.GoogleAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * "Exception Handling". Esse padrão recomenda que, em vez de lançar exceções,
 * sejam retornados objetos de resposta com informações detalhadas sobre o erro.
 * Dessa forma, a aplicação que consumir a API terá informações suficientes
 * para lidar com o erro de maneira apropriada.*/
public enum GoogleAuthServiceErrorMessage implements IGoogleAuthServiceErrorMessage {

    EXTERNAL;
    Logger LOGGER = LoggerFactory.getLogger(GoogleAuthService.class);

    private static final String ERROR_GOOGLE_AUTH_001 = "ERROR_GOOGLE_AUTH_001";
    private static final String ERROR_GOOGLE_AUTH_002 = "ERROR_GOOGLE_AUTH_002";
    private static final String ERROR_GOOGLE_AUTH_003 = "ERROR_GOOGLE_AUTH_003";

    private static final String ERROR_GOOGLE_AUTH_004 = "ERROR_GOOGLE_AUTH_004";

    private static final String ERROR_GOOGLE_AUTH_005 = "ERROR_GOOGLE_AUTH_005";

    private static final String ERROR_GOOGLE_AUTH_006 = "ERROR_GOOGLE_AUTH_006";

    @Override
    public String redirectToGoogleAuthError(RuntimeException ex) {
        LOGGER.debug("Error [{}] ", ERROR_GOOGLE_AUTH_001, ex);

        return ERROR_GOOGLE_AUTH_001;
    }

    @Override
    public String isTokenValidError(Exception ex, String accessToken) {
        LOGGER.debug("Error when trying validate toklen, the accessToken: [{}], " +
                     "the external error is: [{}] " +
                     "the internal error occurred was [{}] ", accessToken, ERROR_GOOGLE_AUTH_002, ex);
        return ERROR_GOOGLE_AUTH_002;
    }

    @Override
    public String extractCodeFromRedirectUrlError(Exception ex, String urlWithCode) {
        LOGGER.debug("Error when trying to extract the url code the parameter sent was: urlWithCode: [{}], " +
                     "the external error is: [{}] " +
                     "the internal error occurred was [{}]", urlWithCode, ERROR_GOOGLE_AUTH_003, ex);
        return ERROR_GOOGLE_AUTH_003;
    }

    @Override
    public String renewTokenError(RuntimeException ex, String accessToken) {
        LOGGER.debug("Error when trying renew token, method parameter: accessToken: [{}], " +
                "the external error is: [{}] " +
                "the internal error occurred was [{}]", accessToken, ERROR_GOOGLE_AUTH_004, ex);
        return ERROR_GOOGLE_AUTH_004;
    }

    @Override
    public String exchangeCodeForTokenError(RuntimeException ex, String loginAuthenticateCode) {
        LOGGER.debug("Error when exchange code for token, method parameter: loginAuthenticateCode: [{}], " +
                "the external error is: [{}] " +
                "the internal error occurred was [{}]", loginAuthenticateCode, ERROR_GOOGLE_AUTH_005, ex);
        return ERROR_GOOGLE_AUTH_005;
    }

    @Override
    public String getGoogleUserIdError(RuntimeException ex, String accessToken) {
        LOGGER.debug("Error when get Google user Id, method parameter: accesssToken: [{}], " +
                "the external error is: [{}] " +
                "the internal error occurred was [{}]", accessToken, ERROR_GOOGLE_AUTH_006, ex);
        return ERROR_GOOGLE_AUTH_006;
    }


}
