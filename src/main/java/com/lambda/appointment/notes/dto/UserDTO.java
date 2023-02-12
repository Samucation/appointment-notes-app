package com.lambda.appointment.notes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("createdDate")
    private LocalDate createdDate;

    @JsonProperty("updatedDate")
    private LocalDate updatedDate;

    @JsonProperty("databaseStatus")
    private Boolean databaseStatus;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("googleUserId")
    private String googleUserId;

    @JsonProperty("googleToken")
    private String googleToken;
}
