package com.gymapp.infrastructure.persistence;

import com.gymapp.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, Long> {

}
