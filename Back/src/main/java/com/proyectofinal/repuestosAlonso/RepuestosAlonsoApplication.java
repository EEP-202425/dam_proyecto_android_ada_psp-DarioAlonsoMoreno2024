package com.proyectofinal.repuestosAlonso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.proyectofinal")
@EnableJpaRepositories("com.proyectofinal.dao")
@EntityScan("com.proyectofinal.dominio")
public class RepuestosAlonsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepuestosAlonsoApplication.class, args);
	}

}
