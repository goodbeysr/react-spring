package com.spring.hibernate.tuto.springHibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringHibernateApplication.class, args);
	}

}

