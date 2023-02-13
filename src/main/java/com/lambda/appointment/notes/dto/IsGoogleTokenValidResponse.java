package com.lambda.appointment.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsGoogleTokenValidResponse {

    Boolean isTokenValid;
    String externalError;

}
