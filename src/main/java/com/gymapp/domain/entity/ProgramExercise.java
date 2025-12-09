package com.gymapp.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "program_exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(nullable = false, length = 2)
    String sets;

    @NotNull
    @Column(nullable = false, length = 5)
    String reps;

    @Column(length = 3)
    String restTime;

    @NotNull
    @Column(name = "workout_day",nullable = false, length = 10)
    String day;

    @Column(length = 15)
    String notes;

    @Column(length = 8)
    String intensity;

    @Column(length = 8)
    String tempo;

    @NotNull
    @Column(nullable = false)
    @Min(1)
    @Max(20)
    Integer position;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id")
    Program program;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id")
    Exercise exercise;

}
