package com.thiago.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.thiago.bookstore.service.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbservice;
	
	@Bean
	public void instanciaBaseDeDados() {
		this.dbservice.instanciaBaseDeDados();
	}

}
