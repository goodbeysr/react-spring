/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.example.controller;

import com.example.example.model.*;
import com.example.example.repos.*;
import com.example.example.utils.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hp
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class EntrepotController {
    @Autowired
    private EntrepotRepo e; 
    
    @Autowired
    private ProductRepo p;
    
    //GET
    @GetMapping("/entrepots")
    public Map<String,Object> getAllEntrepots(){
        Map<String,Object> m= new TreeMap<>();
        m.put("entrepots", e.findAll());
        return m;
    }
    
    @GetMapping("/entrepot/{id}")
    public Map<String,Object> getAllEntrepot(@PathVariable(value = "id") Integer id){
        Map<String,Object> m= new TreeMap<>();
        Entrepot en;
        try{
            en = e.findById(id).get();
            m.put("entrepot", en);
        }catch(Exception ex){
            m.put("message", "Entrepot N'est pas trouver");
        }finally{
            return m;
        }
    }
    
    @GetMapping("/entrepot/products/{id}")
    public Map<String,Object> getEntrepotProducts(@PathVariable(value = "id") Integer id){
        Map<String,Object> m= new TreeMap<>();
        m.put("products", e.findById(id).get());
        return m;
    }
    
    @GetMapping("/entrepot/{ide}/product/{idp}")
    public Map<String,Object> getEntrepotProduct(@PathVariable(value = "ide") Integer ide,@PathVariable(value = "idp") Integer idp){
        Map<String,Object> m= new TreeMap<>();
        Entrepot en= e.findById(ide).get();
        Product pr = p.findById(idp).get();
        for(EntrepotProduct enp : pr.getEntrepotProduct()){
            if(enp.getProduct().getId() == idp){
                m.put("capacite", enp.getCapacite());
                break;
            }
        }
        return m;
    }
    
    
    //POST
    @PostMapping("/entrepots")
    public Map<String,Object> addEntrepot(@Valid @RequestBody Entrepot entrepot){
        Map<String,Object> m= new TreeMap<>();
        try{
            e.save(entrepot);
            m.put("message", "Entrepot Ajouté");
        }catch(Exception ex){
            m.put("message", "Error");
        }finally{
        return m;
        }
    }
    
    
    //PUT
    @PutMapping("/entrepots")
    public Map<String,Object> editEntrepot(@Valid @RequestBody Entrepot entrepot){
        Map<String,Object> m= new TreeMap<>();
        log.info(entrepot.toString());
        Entrepot en;
        try{
            en = e.findById(entrepot.getId()).get();
            en.setAddress(entrepot.getAddress());
            en.setCapacite(entrepot.getCapacite());
            en.setName(entrepot.getName());
            en.setEtat(entrepot.getEtat());
            e.save(en);
            m.put("message", "Entrepot Edité");
        }catch(Exception ex){
            m.put("message", "Entrepot Non trouvé");
        }finally{
            return m;
        }
    }
    
    @PutMapping("/entrepots/etat/{id}")
    public Map<String,Object> changerEtat(@PathVariable(value = "id") Integer id){
        Map<String,Object> m= new TreeMap<>();
        Entrepot en = e.findById(id).get();
        en.setEtat(!en.getEtat());
        e.save(en);
        m.put("message", "Etat s'est bien changé");
        return m;
    }
    
    
    //DELETE
    @DeleteMapping("/entrepots/{id}")
    public Map<String,Object> deleteEntrepot(@PathVariable(value = "id") Integer id){
        Map<String,Object> m= new TreeMap<>();
        Entrepot en = e.findById(id).get();
        if(en != null){
            e.delete(en);
            m.put("message", "Entrepot Deleted");
        }else{
            m.put("message", "Entrepot NOT FOUND");
        }
        return m;
    }
    
    
    //Add Product to Entrepot
    @PostMapping("/addEntrepotProduct/{ide}/{idp}")
    public Map<String,Object> addEntToProd(@PathVariable(value = "ide") Integer ide,@PathVariable(value = "idp") Integer idp,@Valid @RequestBody Capacite c){
        Map<String,Object> m= new TreeMap<>();
        
        Entrepot en = e.findById(ide).get();
        Product pr = p.findById(idp).get();
        log.info(en.toString());
        if(en!=null && pr!=null){
            
            if(en.getEtat()){
                EntrepotProduct ep = new EntrepotProduct(pr,en,c.getCapacite());
            
                if(pr.getEntrepotProduct().contains(ep)){

                    for(EntrepotProduct entrepotP : pr.getEntrepotProduct()){
                        if(entrepotP.getProduct().getId() == pr.getId() && entrepotP.getEntrepot().getId() == en.getId()){
                            if(sum(pr.getId()) + c.getCapacite() <= pr.getQuantite()){
                                if(en.getCapacite() >= en.getPris() + c.getCapacite()){
                                    entrepotP.setCapacite(c.getCapacite());
                                    en.setPris(en.getPris()-entrepotP.getCapacite()+c.getCapacite());
                                    m.put("message","QUANTITE modifié du Produit " + pr.getName());
                                    break;
                                }else{
                                    m.put("message","ENTREPOT FULL");
                                    break;
                                }
                            }else{
                                m.put("message","QUANTITE Produit " + pr.getName() + " N'est pas suffisante");
                            }
                        }
                    }
                    p.save(pr);
                }else{

                    if(sum(pr.getId()) + c.getCapacite() <= pr.getQuantite()){
                        if(en.getCapacite() >= en.getPris() + c.getCapacite()){
                            en.getEntrepotProduct().add(new EntrepotProduct(pr, en, c.getCapacite()));
                            en.setPris(en.getPris()+c.getCapacite());
                            p.save(pr);
                            m.put("message","Produit " +pr.getName()+ " AJOUTER à l'entrepot " + en.getName());
                        }else{
                            m.put("message","ENTREPOT FULL");
                        }
                    }else{
                        m.put("message","QUANTITE Produit " + pr.getName() + " N'est pas suffisante");
                    }
                }

            }else{
                m.put("message","Entrepot or Product Not found!");
            }
            }else{
            m.put("message","Entrepot fermé!");
        }
        
        return m;
    }
    
    public Integer sum(Integer id){
        Product pr = p.findById(id).get();
        Integer sum = 0;
        if(pr!=null){
            for(EntrepotProduct ep : pr.getEntrepotProduct()){
                sum+= ep.getCapacite();
            }
        }
        return sum;
    }
    
    @DeleteMapping("/deleteEntrepotProduct/{ide}/{idp}")
    public Map<String,Object> deleteEntToProd(@PathVariable(value = "ide") Integer ide,@PathVariable(value = "idp") Integer idp,@Valid @RequestBody Capacite c){
        Map<String,Object> m= new TreeMap<>();
        Entrepot en = e.findById(ide).get();
        Product pr = p.findById(idp).get();
        
        if(en !=null && pr != null){
            
            EntrepotProduct ep = new EntrepotProduct(pr,en,0);
            
            if(pr.getEntrepotProduct().contains(ep)){
                List<EntrepotProduct> eps = new ArrayList<>();
                for(EntrepotProduct t : pr.getEntrepotProduct()){
                    if(t.equals(ep)){
                        en.setPris(en.getPris() - c.getCapacite());
                    }else{
                        eps.add(t);
                    }
                }
                m.put("message","Produit " + pr.getName() + " est supprimé de l'entrepot " +en.getName());
                pr.getEntrepotProduct().clear();
                pr.getEntrepotProduct().addAll(eps);
                e.save(en);
            }else{
                m.put("message","Entrepot " + en.getName() + " ne contient pas " + pr.getName());
            }
            
        }else{
            m.put("message","Entrepot or Product Not found!");
        }
        
        return m;
    }
}
