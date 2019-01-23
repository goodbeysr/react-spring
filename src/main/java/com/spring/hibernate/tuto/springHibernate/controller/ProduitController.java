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
import com.spring.hibernate.tuto.springHibernate.repository.produitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProduitController {
    @Autowired
    produitRepository ProduitRepository;
    entrepotRepository EntrepotRepository;

    // Get All Products
    @GetMapping("/produits")
    public List<produit> getAllProduits() {
        return ProduitRepository.findAll();
    }
    
    // Create a new Product
    @PostMapping("/produits")
    public produit createProduit(@Valid @RequestBody produit P) {
        return ProduitRepository.save(P);
    }
    
    // Get a Single Product
    @GetMapping("/produits/{id}")
    public produit getProduitById(@PathVariable(value = "id") Long ProdId) {
        return ProduitRepository.findById(ProdId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", ProdId));
    }
 
    // Get all storqge of a product
    @GetMapping("/produits/{id}/entrepot")
    public List<entrepot> getEntepot(@PathVariable(value = "id") Long ProdId) {
        produit P = ProduitRepository.findById(ProdId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", ProdId));
        return P.getEntrepots();
    }
    
    // Update a Produit
    @PutMapping("/produits/{id}")
    public produit updateProduit(@PathVariable(value = "id") Long ProdId,
                                            @Valid @RequestBody produit ProdDetails) {

        produit P = ProduitRepository.findById(ProdId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", ProdId));

        P.setNomProduit(ProdDetails.getNomProduit());
        P.setVolume(ProdDetails.getVolume());
        P.setQuantite(ProdDetails.getQuantite());
        P.setPrixStock(ProdDetails.getPrixStock());
        P.setDateLimite(ProdDetails.getDateLimite());
        P.setEntrepots(ProdDetails.getEntrepots());

        produit updatedProduit = ProduitRepository.save(P);
        return updatedProduit;
    }
    
    @PutMapping("/produits/{id}/{entrepot}")
    public produit Addentrepot(@PathVariable(value = "id") Long ProdId,@PathVariable(value = "entrepot") Long EntId) {

        produit P = ProduitRepository.findById(ProdId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", ProdId));
        
        entrepot E = EntrepotRepository.findById(EntId)
                .orElseThrow(() -> new ResourceNotFoundException("entrepot", "id", EntId));

        List<entrepot> L = P.getEntrepots();
        L.add(E);
        P.setEntrepots(L);

        produit updatedProduit = ProduitRepository.save(P);
        return updatedProduit;
    }
    
    // Delete a Product
    @DeleteMapping("/produits/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long ProdId) {
    produit P = ProduitRepository.findById(ProdId)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", ProdId));

    ProduitRepository.delete(P);

    return ResponseEntity.ok().build();
}
    
}
