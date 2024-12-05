package com.northcoders.recordshopapi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecordshopapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordshopapiApplication.class, args);
	}
	@Bean
	public OpenAPI todoApiInfo() {
		return new OpenAPI()
				.info(new Info().title("Record Shop")
						.description("excited to browse some albums? This is the API for you! \uD83D\uDE3A")
						.version("v1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
