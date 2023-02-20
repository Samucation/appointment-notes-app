package com.lambda.appointment.notes.config;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@Data
@ApplicationScoped
public class GoogleAuthConfig {

    @ConfigProperty(name = "google.auth2.redirectUri")
    String redirectUri;

    @ConfigProperty(name = "google.auth2.clientSecret")
    String clientSecret;

    @ConfigProperty(name = "google.auth2.googleAuthApplicationUrl")
    String googleAuthApplicationUrl;

    @ConfigProperty(name = "google.auth2.clientId")
    String clientId;

    @ConfigProperty(name = "google.auth2.googleTokenUrlRequest")
    String googleTokenUrlRequest;

    @ConfigProperty(name = "google.auth2.tokenInfoUrl")
    String tokenInfoUrl;

    @ConfigProperty(name = "google.auth2.userInfoUrl")
    String userInfoUrl;

    @ConfigProperty(name = "google.auth2.exchangeCodeForToken.jsonParameter")
    String jsonParameter;

    @ConfigProperty(name = "google.auth2.exchangeCodeForToken.headerName")
    String headerName;

    @ConfigProperty(name = "google.auth2.exchangeCodeForToken.headerValue")
    String headerValue;

    @ConfigProperty(name = "google.auth2.exchangeCodeForToken.requestUrl")
    String requestUrl;

    @ConfigProperty(name = "quarkus.datasource.username")
    String userNameDataBase;

    @ConfigProperty(name = "quarkus.datasource.password")
    String passwordDataBase;

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String jdbcUrl;

    @ConfigProperty(name = "quarkus.flyway.schemas")
    String flywaySchema;

}

