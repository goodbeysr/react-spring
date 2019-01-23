/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.hibernate.tuto.springHibernate.controller;

/**
 *
 * @author pc
 */

import com.spring.hibernate.tuto.springHibernate.exception.ResourceNotFoundException;
import com.spring.hibernate.tuto.springHibernate.model.entrepot;
import com.spring.hibernate.tuto.springHibernate.model.produit;
import com.spring.hibernate.tuto.springHibernate.repository.entrepotRepository;
import static java.util.Arrays.sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EntrepotController {
    @Autowired
    entrepotRepository EntrepotRepository;

    // Get All Notes
    @GetMapping("/entrepots")
    public List<entrepot> getAllEntrepots() {
        return EntrepotRepository.findAll();
    }
    
    // Create a new Note
    @PostMapping("/entrepots")
    public entrepot createEntrepot(@Valid @RequestBody entrepot E) {
        return EntrepotRepository.save(E);
    }
    
    // Get a Single Note
    @GetMapping("/entrepots/{id}")
    public entrepot getEntById(@PathVariable(value = "id") Long EntId) {
        return EntrepotRepository.findById(EntId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrepot", "id", EntId));
    }
    
    @GetMapping("/entrepots/{id}/produit")
    public List<produit> getProdofprod(@PathVariable(value = "id") Long EntId) {
        entrepot E = EntrepotRepository.findById(EntId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrepot", "id", EntId));
        return E.getProduct();
    }
    
    // Update a Note
    @PutMapping("/entrepots/{id}")
    public entrepot updateEnt(@PathVariable(value = "id") Long EntId,
                                            @Valid @RequestBody entrepot EntDetails) {

        entrepot E = EntrepotRepository.findById(EntId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrepot", "id", EntId));

        E.setNomEntrepot(EntDetails.getNomEntrepot());
        E.setAdresse(EntDetails.getAdresse());
        E.setCapacite(EntDetails.getCapacite());
        E.setEtat(EntDetails.isEtat());

        entrepot updatedEntrepot = EntrepotRepository.save(E);
        return updatedEntrepot;
    }
    
     @PutMapping("/entrepots/etat/{id}")
    public entrepot changeEtat(@PathVariable(value = "id") Long EntId) {

        entrepot E = EntrepotRepository.findById(EntId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrepot", "id", EntId));

        E.setEtat(!E.isEtat());

        entrepot updatedEntrepot = EntrepotRepository.save(E);
        return updatedEntrepot;
    }
    // Delete a Note
    @DeleteMapping("/entrepots/{id}")
    public ResponseEntity<?> deleteEnt(@PathVariable(value = "id") Long EntId) {
    entrepot E = EntrepotRepository.findById(EntId)
            .orElseThrow(() -> new ResourceNotFoundException("Entrepot", "id", EntId));

    EntrepotRepository.delete(E);

    return ResponseEntity.ok().build();
}
}
