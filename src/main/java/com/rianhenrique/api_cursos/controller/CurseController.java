package com.rianhenrique.api_cursos.controller;

import com.rianhenrique.api_cursos.dto.CurseEntityDTO;
import com.rianhenrique.api_cursos.entities.CurseEntity;
import com.rianhenrique.api_cursos.repositories.ProfessorRepository;
import com.rianhenrique.api_cursos.service.CurseService;
import com.rianhenrique.api_cursos.service.ProfessorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CurseController {

    @Autowired
    private CurseService curseService;

    @Autowired
    private ProfessorRepository professorRepository;


    @PostMapping("")
    public CurseEntityDTO createCurse(@RequestBody CurseEntity curseEntity) {

        CurseEntityDTO  curseEntityDTO = new CurseEntityDTO();
        BeanUtils.copyProperties(curseEntity, curseEntityDTO);


        this.professorRepository.findById(curseEntity.getProfessorId()).orElseThrow(()-> {
            throw new RuntimeException("Professor não encontrado!");
        });

            var resultado = this.curseService.save(curseEntity);

            if(curseEntity.getName().isEmpty() || curseEntity.getCategory().isEmpty()){
                throw new RuntimeException("Falta o nome ou a categoria do Curso!");

            }

            curseEntityDTO.setActive(resultado.isActive());
            curseEntityDTO.setCreatedAt(resultado.getCreatedAt());
            curseEntityDTO.setUpdatedAt(resultado.getUpdatedAt());
            curseEntityDTO.setName(curseEntity.getName());
            curseEntityDTO.setCategory(curseEntity.getCategory());
            curseEntityDTO.setProfessorId(curseEntity.getProfessorId());

        return curseEntityDTO;

    }

    @GetMapping("")
    public List<CurseEntityDTO> getCurses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ) {
        List<CurseEntity> curses;

        if(name != null && category != null) {
            curses = curseService.findByNameAndCategory(name, category);

        } else {
            curses = curseService.findAll();
        }

        return curses.stream().map(c -> {
            CurseEntityDTO dto = new CurseEntityDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).toList();
    }


    @PutMapping("/:id")
    public CurseEntityDTO updateCurse(@PathVariable Long id, @RequestBody String name, @RequestBody String category) {

        CurseEntityDTO curseEntityDTO = new CurseEntityDTO();

        var curso = this.curseService.findById(id);

        BeanUtils.copyProperties(curso, curseEntityDTO);

        if (name != null && category != null) {
            curso.setName(name);
            curso.setCategory(category);
            curso.setUpdatedAt(LocalDateTime.now());
            this.curseService.save(curso);
        } else if (name != null && category == null) {
            curso.setName(name);
            curso.setUpdatedAt(LocalDateTime.now());

            this.curseService.save(curso);
        } else if (name == null && category != null) {
            curso.setCategory(category);
            curso.setUpdatedAt(LocalDateTime.now());
            this.curseService.save(curso);
        }

        return curseEntityDTO;
    }

    @DeleteMapping("/:id")
    public void deleteCurse(@PathVariable Long id) {
        var curse = this.curseService.findById(id);

        if(curse != null) {
        this.curseService.deleteCurse(curse);
        }

    }

    @PatchMapping("/:id/active")
    public CurseEntityDTO patchCurse(@PathVariable Long id) {
        CurseEntityDTO curseEntityDTO = new CurseEntityDTO();
        var curse = this.curseService.toogleStatusCurse(id);

        BeanUtils.copyProperties(curseEntityDTO, curse);

        return curseEntityDTO;

    }


}
