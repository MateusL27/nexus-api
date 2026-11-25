package nexus_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NexusApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexusApiApplication.class, args);
	}

}
