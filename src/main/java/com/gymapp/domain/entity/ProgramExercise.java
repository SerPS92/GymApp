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
public class ProgramExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int reps;
    int sets;
    int restTime;
    String day;
    String notes;

    @ManyToOne
    @JoinColumn(name = "id") // Falta en tu código, es necesario para que apunte a Programa
    Program program;

    @ManyToOne
    @JoinColumn(name = "id")
    Exercise exercise;



}
