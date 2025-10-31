package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseJpaRepository extends JpaRepository<Exercise, Long> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);


    Page<Exercise> findByDifficulty(Difficulty difficulty, Pageable pageable);
    Page<Exercise> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<Exercise> findByName(String name);

    @Query("""
       SELECT DISTINCT e
       FROM Exercise e
       JOIN e.categories c
       WHERE c.id IN :categoryIds
       """)
    Page<Exercise> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("""
        SELECT DISTINCT e
        FROM Exercise e
        JOIN e.categories c
        WHERE c.id IN :categoryIds
        AND e.difficulty = :difficulty
        """)
    Page<Exercise> findByCategoryIdsAndDifficulty(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("difficulty") Difficulty difficulty,
            Pageable pageable);
}
