package com.example.mytimeplan.repository;

import com.example.mytimeplan.model.StarJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarRepository extends JpaRepository<StarJpa, Long> {
}
