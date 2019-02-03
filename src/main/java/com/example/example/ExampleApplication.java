package com.example.example;

import com.example.example.model.*;
import com.example.example.repos.*;
import java.util.Date;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ExampleApplication {
    
    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private EntrepotRepo entrepotRepository;

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}
        
    
    
    public void run(String... strings) {
        // Create
        Entrepot en1 = entrepotRepository.findById(4).get();
        entrepotRepository.save(en1);
        log.info(en1.toString()); // it is not null

        Entrepot en2 = entrepotRepository.findById(7).get();
        entrepotRepository.save(en2);
        log.info(en2.toString()); // it is not null

        Product p = productRepository.findById(2).get();
        en1.getEntrepotProduct().add(new EntrepotProduct(p, en1, 111));
    }
}

