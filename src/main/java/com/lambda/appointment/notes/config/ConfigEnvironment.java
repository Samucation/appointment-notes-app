package com.lambda.appointment.notes.config;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@Data
@ApplicationScoped
public class ConfigEnvironment {

    @ConfigProperty(name = "SQL_SERVER_DB_URL")
    String sqlServerDbUrl;

    @ConfigProperty(name = "SQL_SERVER_USER")
    String sqlServerDataBaseUser;

    @ConfigProperty(name = "SQL_SERVER_PASS")
    String sqlServerDataBasePass;

    @ConfigProperty(name = "SCHEMA_DB_NAME")
    String sqlServerDataBaseSchemaName;

}
