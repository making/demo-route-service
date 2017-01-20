package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoRouteServiceApplication {

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.requestFactory(new BufferingClientHttpRequestFactory(
						new TrustEverythingClientHttpRequestFactory()))
				.errorHandler(new NoErrorsResponseErrorHandler()).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoRouteServiceApplication.class, args);
	}
}
