package com.aaslin.cbt.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.aaslin.cbt.common.model")
@EnableJpaRepositories(basePackages = { "com.aaslin.cbt.developer","com.aaslin.cbt.common","com.aaslin.cbt.super_admin","com.aaslin.cbt.participant"})
@ComponentScan(basePackages = { "com.aaslin.cbt.developer","com.aaslin.cbt.common","com.aaslin.cbt.super_admin","com.aaslin.cbt.participant"})
public class ComputerBasedTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(ComputerBasedTestApplication.class, args);
	}
}
