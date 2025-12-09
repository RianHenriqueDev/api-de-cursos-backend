package com.rianhenrique.api_cursos.repositories;

import com.rianhenrique.api_cursos.entities.CurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurseRepository extends JpaRepository<CurseEntity,Long> {

    Optional<CurseEntity> findByNameIgnoreCase(String name);
    List<CurseEntity> findByNameIgnoreCaseAndCategoryIgnoreCase(String name,String category);
    Optional<CurseEntity> findById(String name);


}
