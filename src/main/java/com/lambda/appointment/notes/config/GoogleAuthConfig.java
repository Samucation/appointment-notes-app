package com.lambda.appointment.notes.config;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@Data
@ApplicationScoped
public class GoogleAuthConfig {

    @ConfigProperty(name = "google.auth.redirectUri")
    String redirectUri;

    @ConfigProperty(name = "google.auth.clientSecret")
    String clientSecret;

    @ConfigProperty(name = "google.auth.googleAuthApplicationUrl")
    String googleAuthApplicationUrl;

    @ConfigProperty(name = "google.auth.clientId")
    String clientId;

    @ConfigProperty(name = "google.auth.googleTokenUrlRequest")
    String googleTokenUrlRequest;
}

