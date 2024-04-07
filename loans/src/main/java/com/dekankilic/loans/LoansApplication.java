package com.dekankilic.loans;

<<<<<<< HEAD
import com.dekankilic.loans.dto.LoanContactInfoDto;
=======
>>>>>>> 9a62807be0f4e2ae82ac7f17c04ffb4ccc63b711
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.context.properties.EnableConfigurationProperties;
=======
>>>>>>> 9a62807be0f4e2ae82ac7f17c04ffb4ccc63b711
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loan microservice REST API Documentation",
				description = "DEKANBANK Loan microservice REST API Documentation",
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
				description = "DEKANBANK Loan microservice REST API Documentation",
				url = "http://www.dekanbank.com/swagger-ui.html"
		)
)
<<<<<<< HEAD
@EnableConfigurationProperties(value = {LoanContactInfoDto.class})
=======
>>>>>>> 9a62807be0f4e2ae82ac7f17c04ffb4ccc63b711
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
