package org.suhodo.boot01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Boot01Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot01Application.class, args);
	}

}
