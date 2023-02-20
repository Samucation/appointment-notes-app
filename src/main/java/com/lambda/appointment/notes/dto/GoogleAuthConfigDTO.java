package com.lambda.appointment.notes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;

@Data
@ApplicationScoped
public class GoogleAuthConfigDTO {

    @JsonProperty("redirectUri")
    String redirectUri;

    @JsonProperty("clientSecret")
    String clientSecret;

    @JsonProperty("googleAuthApplicationUrl")
    String googleAuthApplicationUrl;

    @JsonProperty("clientId")
    String clientId;

    @JsonProperty("googleTokenUrlRequest")
    String googleTokenUrlRequest;

    @JsonProperty("tokenInfoUrl")
    String tokenInfoUrl;

    @JsonProperty("userInfoUrl")
    String userInfoUrl;

    @JsonProperty("headerName")
    String headerName;

    @JsonProperty("headerValue")
    String headerValue;

    @JsonProperty("requestUrl")
    String requestUrl;

    @JsonProperty("jsonParameter")
    String jsonParameter;

    @JsonProperty("userNameDataBase")
    String userNameDataBase;

    @JsonProperty("passwordDataBase")
    String passwordDataBase;

    @JsonProperty("jdbcUrl")
    String jdbcUrl;

    @JsonProperty("flywaySchema")
    String flywaySchema;

}

