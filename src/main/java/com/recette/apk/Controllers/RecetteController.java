package com.recette.apk.controllers;

import com.recette.apk.models.Recette;
import com.recette.apk.repositories.RecetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recettes")
public class RecetteController {
    @Autowired
    private RecetteRepository recetteRepository;

    @GetMapping
    public List<Recette> getAllRecettes() {
        return recetteRepository.findAll();
    }

    @PostMapping
    public Recette addRecette(@RequestBody Recette recette) {
        return recetteRepository.save(recette);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recette> getRecetteById(@PathVariable Long id) {
        return recetteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recette> updateRecette(@PathVariable Long id, @RequestBody Recette recetteDetails) {
        return recetteRepository.findById(id).map(recette -> {
            recette.setNom(recetteDetails.getNom());
            recette.setIngredients(recetteDetails.getIngredients());
            recette.setPrix(recetteDetails.getPrix());
            recette.setPays(recetteDetails.getPays());
            recette.setSaison(recetteDetails.getSaison());
            return ResponseEntity.ok(recetteRepository.save(recette));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecette(@PathVariable Long id) {
        return recetteRepository.findById(id).map(recette -> {
            recetteRepository.delete(recette);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
