package com.lambda.appointment.notes.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetGoogleAuthUrlResponse {

    String urlGoogleAuth;
    String externalError;

    public GetGoogleAuthUrlResponse(String urlGoogleAuth) {
        this.urlGoogleAuth = urlGoogleAuth;
    }

    public GetGoogleAuthUrlResponse(String urlGoogleAuth, String externalError) {
        this.urlGoogleAuth = urlGoogleAuth;
        this.externalError = externalError;
    }

}
