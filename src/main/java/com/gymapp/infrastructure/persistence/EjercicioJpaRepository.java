package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioJpaRepository extends JpaRepository<Ejercicio, Long> {

}
