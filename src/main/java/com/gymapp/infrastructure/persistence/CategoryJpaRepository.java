package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);
    Optional<Category> findByNameIgnoreCase(String name);

}
