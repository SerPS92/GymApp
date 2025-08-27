package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.MuscleGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseJpaRepository extends JpaRepository<Exercise, Long> {

    boolean existsByNameIgnoreCaseAndMuscleGroup(String name, MuscleGroup muscleGroup);

    Page<Exercise> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Exercise> findByMuscleGroup(MuscleGroup muscleGroup, Pageable pageable);
}
