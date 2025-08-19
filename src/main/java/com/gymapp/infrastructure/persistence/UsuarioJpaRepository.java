package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<User, Long> {

}
