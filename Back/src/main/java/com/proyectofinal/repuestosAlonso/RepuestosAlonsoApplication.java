//package com.proyectofinal.repuestosAlonso;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//
//// Para que encuentre @Entity en com.proyectofinal.dominio
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//// Para que registre tus interfaces JpaRepository en com.proyectofinal.dao
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//@SpringBootApplication(scanBasePackages = "com.proyectofinal", exclude = { SecurityAutoConfiguration.class })
//@EntityScan(basePackages = "com.proyectofinal.dominio")
//@EnableJpaRepositories(basePackages = "com.proyectofinal.dao")
//public class RepuestosAlonsoApplication {
//	public static void main(String[] args) {
//		SpringApplication.run(RepuestosAlonsoApplication.class, args);
//	}
//}



package com.proyectofinal.repuestosAlonso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.proyectofinal")
@EntityScan(basePackages = "com.proyectofinal.dominio")
@EnableJpaRepositories(basePackages = "com.proyectofinal.dao")
public class RepuestosAlonsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepuestosAlonsoApplication.class, args);
    }
}