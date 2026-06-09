package org.example.educambiental;

import org.example.educambiental.service.CentroReciclajeService;
import org.example.educambiental.repository.UsuarioRepository;
import org.example.educambiental.entity.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	CommandLineRunner init(CentroReciclajeService centroReciclajeService, 
						   UsuarioRepository usuarioRepository, 
						   PasswordEncoder passwordEncoder) {
		return args -> {
			// 1. Crear Administrador por defecto si no existe
			if (!usuarioRepository.existsByCorreo("admin@educambiental.com")) {
				Usuario admin = Usuario.builder()
						.nombre("Administrador del Sistema")
						.correo("admin@educambiental.com")
						.password(passwordEncoder.encode("Admin123*"))
						.rol("ADMIN_SYSTEM")
						.enabled(true)
						.build();
				usuarioRepository.save(admin);
				System.out.println(">>> Usuario Administrador creado: admin@educambiental.com / Admin123*");
			}

			// 2. Sincronizar centros externos (CDMX)
			centroReciclajeService.sincronizarCentrosExternos();
		};
	}

}
