package com.lambda.appointment.notes.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "perfilUsuario", schema = "appointmentNotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends PanacheEntity {

    @GeneratedValue
    private Long id;

    @Column(name = "dataCriacao")
    private LocalDate createdDate;

    @Column(name = "dataAtualizacao")
    private LocalDate updatedDate;

    @Column(name = "statusBD")
    private Boolean databaseStatus;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", referencedColumnName = "id", nullable = true)
    private User usuario;

}


