package com.br.fiap.postech.ht_autenticador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = 
@Info(title = "Hackathon API - Autenticador", 
	version = "1.0", 
	description = "Página de referência do Hackathon API - Autenticador")
)
public class HtAutenticadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtAutenticadorApplication.class, args);
	}

}
