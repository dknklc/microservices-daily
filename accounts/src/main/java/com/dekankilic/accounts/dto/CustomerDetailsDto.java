package com.dekankilic.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Card and Loan information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer",
            example = "Dekan Kilic"
    )
    @NotEmpty(message = "Name cannot be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer",
            example = "dekan.kilic@gmail.com"
    )
    @NotEmpty(message = "Email address cannot be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "9345432123"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountDto accountDto;
    @Schema(
            description = "Loan details of the customer"
    )
    private LoanDto loanDto;
    @Schema(
            description = "Card details of the customer"
    )
    private CardDto cardDto;
}
