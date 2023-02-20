package com.lambda.appointment.notes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;

@Data
@ApplicationScoped
public class ConfigEnvironmentDTO {

    @JsonProperty("sqlServerDbUrl")
    String sqlServerDbUrl;

    @JsonProperty("sqlServerDataBaseUser")
    String sqlServerDataBaseUser;

    @JsonProperty("sqlServerDataBasePass")
    String sqlServerDataBasePass;

    @JsonProperty("sqlServerDataBaseSchemaName")
    String sqlServerDataBaseSchemaName;
}
