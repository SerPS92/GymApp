package com.gymapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "programas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Program {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate startDate;
    LocalDate endDate;

    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProgramExercise> programExercises;

    @ManyToOne
    @JsonBackReference
    User user;
}
