package com.rianhenrique.api_cursos.controller;

import com.rianhenrique.api_cursos.entities.ProfessorEntity;
import com.rianhenrique.api_cursos.repositories.ProfessorRepository;
import com.rianhenrique.api_cursos.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping("")
    public ProfessorEntity createProfessor(@RequestBody ProfessorEntity professorEntity){
        var result = this.professorService.save(professorEntity);
        return result.getBody();
    }
}
