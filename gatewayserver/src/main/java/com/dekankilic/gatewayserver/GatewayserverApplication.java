package com.dekankilic.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	// Instead of /accounts/**, we can have custom requirement like /dekanbank/accounts/**.
	// Whenever you have these kind of custom or dynamic routing related requirements, then in such scenarios, Spring Cloud Gateway also gives flexibility to developers to define their own routing configurations.
	@Bean
	public RouteLocator dekanBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/dekanbank/accounts/**")
						.filters(f -> f.rewritePath("/dekanbank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString())
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contactSupport"))) // Whenever there is an exception happens, please invoke this fallback by forwarding the request to the contactSupport.
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/dekanbank/loans/**")
						.filters(f -> f.rewritePath("/dekanbank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/dekanbank/cards/**")
						.filters(f -> f.rewritePath("/dekanbank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						.uri("lb://CARDS"))
				.build();
	}

}
// Let's try to create our own custom filters. (Inside filters package)
// The logic is that as soon as the external client makes a request, I want to create a correlation id, and I want to
// send(by setting it in request header) this id from gateway server to other microservices like from gateway to account, and card and loan.
// Finally, I want to send the correlationId back to the client(by setting it in response header).
// It will be like trace id.