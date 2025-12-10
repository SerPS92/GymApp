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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Column(length = 35)
    String title;

    @ElementCollection
    @CollectionTable(name = "program_notes", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "note", length = 80)
    List<String> notes = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProgramExercise> programExercises;

    @ElementCollection
    @CollectionTable(name = "program_day_labels", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "label", length = 30)
    @MapKeyColumn(name = "day_key", length = 30)
    Map<String, String> dayLabels = new HashMap<>();

    @ManyToOne
    @JsonBackReference
    Client client;
}
