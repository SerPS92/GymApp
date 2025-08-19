package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.ProgramExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaEjercicioJpaRepository extends JpaRepository<ProgramExercise, Long> {

}
