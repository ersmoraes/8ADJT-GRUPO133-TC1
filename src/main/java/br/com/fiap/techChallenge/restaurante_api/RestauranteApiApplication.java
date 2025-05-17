package br.com.fiap.techChallenge.restaurante_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Restaurante API",
				version = "1.0",
				description = "API para gerenciamento de restaurantes"
		))
public class RestauranteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestauranteApiApplication.class, args);
	}
}
