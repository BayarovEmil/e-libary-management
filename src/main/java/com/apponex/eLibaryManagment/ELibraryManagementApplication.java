package com.apponex.eLibaryManagment;

import com.apponex.eLibaryManagment.dataAccess.auth.UserRepository;
import com.apponex.eLibaryManagment.core.entity.Role;
import com.apponex.eLibaryManagment.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ELibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELibraryManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(
			UserRepository userRepository,
			Environment environment,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			String username = environment.getProperty("ADMIN_USERNAME");
			String email = environment.getProperty("ADMIN_EMAIL");
			String password = environment.getProperty("ADMIN_PASSWORD");

			if (userRepository.findByEmail(email).isEmpty()) {
				userRepository.save(
						User.builder()
								.firstname(username)
								.email(email)
								.password(passwordEncoder.encode(password))
								.role(Role.ADMIN)
								.enabled(true)
								.build()
				);
			}
		};
	}
}
