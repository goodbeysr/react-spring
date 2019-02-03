/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.example.repos;

import com.example.example.model.Entrepot;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hp
 */
public interface EntrepotRepo extends JpaRepository<Entrepot, Integer>{
    
}
