package com.rianhenrique.api_cursos.repositories;

import com.rianhenrique.api_cursos.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity,Long> {

    Optional<ProfessorEntity> findById(Long id);
    Optional<ProfessorEntity> findByName(String name);
}
