package com.rianhenrique.api_cursos.service;

import com.rianhenrique.api_cursos.dto.CurseEntityDTO;
import com.rianhenrique.api_cursos.entities.CurseEntity;
import com.rianhenrique.api_cursos.repositories.CurseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurseService {

    @Autowired
    private CurseRepository curseRepository;


    public CurseEntity save(CurseEntity curseEntity) {
        var resultado = this.curseRepository.findByNameIgnoreCase( curseEntity.getName());

        if(resultado.isPresent()) {

            new RuntimeException("Erro ao salvar o curso!");
        }

        var newCurseEntity = this.curseRepository.save(curseEntity);
        return newCurseEntity;
    }


    public List<CurseEntity> findAll() {
        return this.curseRepository.findAll();
    }

    public List<CurseEntity> findByNameAndCategory(String name,String category) {
        if(this.curseRepository.findByNameIgnoreCaseAndCategoryIgnoreCase(name,category).isEmpty()) {
            throw new RuntimeException("Curso não encontrado!");
        }

        return this.curseRepository.findByNameIgnoreCaseAndCategoryIgnoreCase(name,category);

    }

    public CurseEntity findById(Long id) {
        return this.curseRepository.findById(id).orElse(null);
    }

    public void deleteCurse(CurseEntity curseEntity) {
        this.curseRepository.delete(curseEntity);
    }

    public CurseEntity toogleStatusCurse(Long id) {

        var curse = this.curseRepository.findById(id).orElse(null);

        if(curse.isActive()) {
            curse.setActive(false);
            curse.setUpdatedAt(LocalDateTime.now());
            this.curseRepository.save(curse);
        }else {
            curse.setActive(true);
            curse.setUpdatedAt(LocalDateTime.now());
            this.curseRepository.save(curse);
        }

        return curse;
    }



}
