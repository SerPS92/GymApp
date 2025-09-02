package com.gymapp.domain.entity;

import com.gymapp.domain.enums.MuscleGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "exercises", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "muscle_group"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MuscleGroup muscleGroup;

    //TODO imagen
    @Column(length = 1000)
    String description;
}
