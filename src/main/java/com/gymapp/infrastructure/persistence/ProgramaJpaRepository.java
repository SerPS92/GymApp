package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaJpaRepository extends JpaRepository<Programa, Long> {

}
