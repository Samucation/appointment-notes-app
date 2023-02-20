package com.lambda.appointment.notes.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExchangeGoogleCodeForTokenResponse {

    String accessToken;
    String externalError;

    public ExchangeGoogleCodeForTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public ExchangeGoogleCodeForTokenResponse(String accessToken, String externalError) {
        this.accessToken = accessToken;
        this.externalError = externalError;
    }

}
