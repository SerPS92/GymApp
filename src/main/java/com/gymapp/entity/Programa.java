package com.gymapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "programas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Programa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long programaId;
    ZonedDateTime fechaInicio;
    ZonedDateTime fechaFin;

    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProgramaEjercicio> programaEjercicioList;

    @ManyToOne
    @JsonBackReference
    Usuario usuario;
}
