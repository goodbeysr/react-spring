/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.example.controller;

import com.example.example.model.*;
import com.example.example.repos.*;
import com.example.example.utils.*;
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
public class ProductController {
    @Autowired
    private EntrepotRepo e; 
    
    @Autowired
    private ProductRepo p;
    
    
    @GetMapping("/product/{id}")
    public Map<String,Object> getProduct(@PathVariable(value = "id") Integer id){
        Map<String,Object> m= new TreeMap<>();
        Product pr;
        try{
            pr = p.findById(id).get();
            m.put("product", pr);
        }catch(Exception ex){
            m.put("message", "Product N'est pas trouvé");
        }finally{
            return m;
        }
    }
    
    //GET
    @GetMapping("/products")
    public Map<String,Object> getAllEntrepots(){
        Map<String,Object> m= new TreeMap<>();
        m.put("products", p.findAll());
        return m;
    }
    
    //POST
    @PostMapping("/products")
    public Map<String,Object> addEntrepot(@Valid @RequestBody Product product){
        Map<String,Object> m= new TreeMap<>();
        p.save(product);
        log.info("hey");
        m.put("message", "Product Ajouté");
        return m;
    }
    
    //PUT
    @PutMapping("/products")
    public Map<String,Object> editProduct(@Valid @RequestBody Product product){
        Map<String,Object> m= new TreeMap<>();
        log.info(product.toString());
        Product pr;
        try{
            pr = p.findById(product.getId()).get();
            pr.setCreatedAt(product.getCreatedAt());
            pr.setName(product.getName());
            pr.setPrice(product.getPrice());
            pr.setQuantite(product.getQuantite());
            pr.setVolume(product.getVolume());
            p.save(pr);
            m.put("message", "Product Edité");
        }catch(Exception ex){
            m.put("message", "Product Non trouvé");
        }finally{
            return m;
        }
    }
    
    //DELETE
    @DeleteMapping("/products/{id}")
    public Map<String,Object> deleteProduct(@PathVariable(value = "id") Integer id){
        Map<String,Object> m= new TreeMap<>();
        Product pr = p.findById(id).get();
        if(pr != null){
            p.delete(pr);
            m.put("message", "Product Deleted");
        }else{
            m.put("message", "Product NOT FOUND");
        }
        return m;
    }
}
