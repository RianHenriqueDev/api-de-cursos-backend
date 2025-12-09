package com.rianhenrique.api_cursos.service;

import com.rianhenrique.api_cursos.entities.ProfessorEntity;
import com.rianhenrique.api_cursos.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;


    public ResponseEntity<ProfessorEntity> save(ProfessorEntity professorEntity) {
        var professorExists = this.professorRepository.findByName(professorEntity.getName());
        if(professorExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(professorEntity);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.professorRepository.save(professorEntity));
    }

}
