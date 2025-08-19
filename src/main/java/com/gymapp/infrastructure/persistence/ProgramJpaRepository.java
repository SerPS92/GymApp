package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramJpaRepository extends JpaRepository<Program, Long> {

}
