package com.yutong.springgateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringgatewayApplication {

	@Value("${test.uri}")
	private String uri;


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				//basic proxy
				.route(r -> r.path("/**")
						.uri(uri)
				).build();
	}



	public static void main(String[] args) {
		SpringApplication.run(SpringgatewayApplication.class, args);
	}

}
