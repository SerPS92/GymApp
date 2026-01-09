package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramJpaRepository extends JpaRepository<Program, Long> {

    Page<Program> findByOwnerId(Long ownerId, Pageable pageable);
    Optional<Program> findByIdAndOwnerId(Long id, Long ownerId);


    Page<Program> findByOwnerIdAndTitleContainingIgnoreCase(
            Long ownerId,
            String title,
            Pageable pageable
    );

    Page<Program> findByOwnerIdAndClientNameContainingIgnoreCase(
            Long ownerId,
            String clientName,
            Pageable pageable
    );

    Page<Program> findByOwnerIdAndTitleContainingIgnoreCaseAndClientNameContainingIgnoreCase(
            Long ownerId,
            String title,
            String clientName,
            Pageable pageable
    );
}

