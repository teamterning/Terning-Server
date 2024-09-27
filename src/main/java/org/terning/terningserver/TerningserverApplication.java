package org.terning.terningserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TerningserverApplication {

	//terning.app
	public static void main(String[] args) {
		SpringApplication.run(TerningserverApplication.class, args);
	}

}
