package com.lambda.appointment.notes.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario", schema = "appointmentNotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends PanacheEntity {

    @GeneratedValue
    private Long id;

    @Column(name = "dataCriacao")
    private LocalDate createdDate;

    @Column(name = "dataAtualizacao")
    private LocalDate updatedDate;

    @Column(name = "statusBD")
    private Boolean databaseStatus;

    @Column(name = "nome")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "googleId")
    private String googleUserId;

    @Column(name = "googleToken")
    private String googleToken;

}