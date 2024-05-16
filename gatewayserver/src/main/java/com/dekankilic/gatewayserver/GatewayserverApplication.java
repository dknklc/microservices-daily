package com.dekankilic.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
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
						.uri("http://accounts:8080"))
				.route(p -> p
						.path("/dekanbank/loans/**")
						.filters(f -> f.rewritePath("/dekanbank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString())
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET).setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
						.uri("http://loans:8090"))
				.route(p -> p
						.path("/dekanbank/cards/**")
						.filters(f -> f.rewritePath("/dekanbank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString())
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver())))
						.uri("http://cards:9000"))
				.build();
	}

	// In order to change the the default time limiter config of circuit breaker which is 1 second.
	// With this, my circuit breaker will wait maximum 4 seconds for a particular operation to complete.
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}


	@Bean
	public RedisRateLimiter redisRateLimiter(){ // The configuration of Redis Rate Limiter. With this configuration, we are going to add 1 token at each second, and also burst capacity will be 1, and the cost of each request will be 1. With this, it is pretty clear for each second, my end user can only make one request.
		return new RedisRateLimiter(1, 1, 1);
	}

	@Bean
	KeyResolver userKeyResolver(){ // I am providing a key based on which my RateLimiter pattern has to work.
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user")).defaultIfEmpty("anonymous");
	}


}
// Let's try to create our own custom filters. (Inside filters package)
// The logic is that as soon as the external client makes a request, I want to create a correlation id, and I want to
// send(by setting it in request header) this id from gateway server to other microservices like from gateway to account, and card and loan.
// Finally, I want to send the correlationId back to the client(by setting it in response header).
// It will be like trace id.