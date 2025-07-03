package com.fr.spring.groupwork.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fr.spring.groupwork.models.Classe;
import com.fr.spring.groupwork.services.ClasseService;

/**
 * Controller pour gérer les opérations CRUD sur les classes.
 */
@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    public ResponseEntity<Classe> createClasse(
            @RequestParam Long optionId) {
        Classe created = classeService.createClasse(optionId);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable Long id) {
        Optional<Classe> opt = classeService.getClasseById(id);
        return opt.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
