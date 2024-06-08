package org.webppo.clubcommunity_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ClubCommunityBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubCommunityBackendApplication.class, args);
	}

}
