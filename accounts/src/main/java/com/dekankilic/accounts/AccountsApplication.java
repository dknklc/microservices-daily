package com.dekankilic.accounts;

import com.dekankilic.accounts.dto.AccountContactInfoDto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // tell Spring Boot Framework please activate the JPA Auditing and please leverage the bean with the name auditAwareImpl to understand the current auditor.
@OpenAPIDefinition(
		info = @Info(
				title = "Account microservice REST API Documentation",
				description = "DEKANBANK Account microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Dekan KILIC",
						email = "dekan.kilic@gmail.com",
						url = "https://github.com/dknklc"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/dknklc"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "DEKANBANK Account microservice REST API Documentation",
				url = "http://www.dekanbank.com/swagger-ui.html"
		)
)
@EnableConfigurationProperties(value = {AccountContactInfoDto.class}) // To enable the @ConfigurationProperties which is the third option to read the properties from application.properties/yml.
@EnableFeignClients // This will enable the feign client related functionality inside the account microservice. With that, the account ms can connect with other microservices like loans and cards.
@EnableDiscoveryClient // This is for Spring Cloud Kubernetes, which is server-side server discovery client.
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
