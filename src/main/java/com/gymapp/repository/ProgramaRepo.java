package com.gymapp.repository;

import com.gymapp.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaRepo extends JpaRepository<Programa, Long> {

}
