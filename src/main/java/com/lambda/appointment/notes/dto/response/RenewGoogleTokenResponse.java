package com.lambda.appointment.notes.dto.response;


import lombok.Data;


@Data
public class RenewGoogleTokenResponse {

    Boolean tokenStatus;
    String tokenReloaded;
    String externalError;


    public RenewGoogleTokenResponse(Boolean tokenStatus, String externalError, String tokenReloaded) {
        this.tokenStatus = tokenStatus;
        this.externalError = externalError;
        this.tokenReloaded = tokenReloaded;
    }

    public RenewGoogleTokenResponse(Boolean tokenStatus, String tokenReloaded) {
        this.tokenStatus = tokenStatus;
        this.tokenReloaded = tokenReloaded;
    }
}
