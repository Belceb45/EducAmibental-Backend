package org.example.educambiental;

import org.example.educambiental.service.CentroReciclajeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EducAmbientalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducAmbientalApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CentroReciclajeService centroReciclajeService) {
		return args -> {
			centroReciclajeService.sincronizarCentrosExternos();
		};
	}

}
