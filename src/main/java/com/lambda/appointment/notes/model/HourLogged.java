package com.lambda.appointment.notes.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hoursLogged", schema = "appointmentNotes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourLogged extends PanacheEntity {

    @GeneratedValue
    private Long id;

    @Column(name = "createdDate")
    private LocalDate createdDate;

    @Column(name = "updatedDate")
    private LocalDate updatedDate;

    @Column(name = "databaseStatus")
    private Boolean databaseStatus;

    private Integer hours;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", referencedColumnName = "id", nullable = true)
    private User usuario;

}
