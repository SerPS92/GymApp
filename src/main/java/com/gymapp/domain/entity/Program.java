package com.gymapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programs")
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

    @Column(length = 100)
    String title;

    @ElementCollection
    @CollectionTable(name = "program_notes", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "note")
    List<String> notes = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProgramExercise> programExercises;

    @ManyToOne
    @JsonBackReference
    Client client;
}
