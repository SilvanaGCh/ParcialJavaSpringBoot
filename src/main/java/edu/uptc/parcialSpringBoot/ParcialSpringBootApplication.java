package edu.uptc.parcialSpringBoot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API Sistema de Ventas",
				version = "1.0",
				description = "API para gestionar ventas, clientes, productos e inventario"
		)
)
public class ParcialSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcialSpringBootApplication.class, args);
	}
}
