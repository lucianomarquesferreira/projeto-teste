package br.com.backendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BackendApiApplication {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BackendApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApiApplication.class, args);
	}

}
