/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.hibernate.tuto.springHibernate.repository;

import com.spring.hibernate.tuto.springHibernate.model.entrepot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pc
 */
@Repository
public interface entrepotRepository extends JpaRepository<entrepot, Long>{
    
}
