package com.gymapp.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "programa_ejercicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramaEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int repeticiones;
    int series;
    int descanso;
    String dia;
    String notas;

    @ManyToOne
    @JoinColumn(name = "programaId") // Falta en tu código, es necesario para que apunte a Programa
    Programa programa;

    @ManyToOne
    @JoinColumn(name = "ejercicioId")
    Ejercicio ejercicio;



}
