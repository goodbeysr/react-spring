/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.example.utils;

/**
 *
 * @author hp
 */
public class Capacite {
    private Integer capacite;
    
    public Capacite() {
        
    }

    public Capacite(Integer capacite) {
        this.capacite = capacite;
    }
    
    public Capacite(String capacite) {
        this.capacite = Integer.parseInt(capacite);
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }
    
    
}
