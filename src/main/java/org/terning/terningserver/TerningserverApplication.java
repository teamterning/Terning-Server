package org.terning.terningserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TerningserverApplication {

	public static void main(String[] args) { //run
		SpringApplication.run(TerningserverApplication.class, args);
	}

}
