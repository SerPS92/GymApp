package com.gymapp.repository;

import com.gymapp.entity.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepo extends JpaRepository<Ejercicio, Long> {

}
