package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioJpaRepository extends JpaRepository<Exercise, Long> {

}
