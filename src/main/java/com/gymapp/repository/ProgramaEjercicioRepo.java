package com.gymapp.repository;

import com.gymapp.entity.ProgramaEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaEjercicioRepo extends JpaRepository<ProgramaEjercicio, Long> {

}
