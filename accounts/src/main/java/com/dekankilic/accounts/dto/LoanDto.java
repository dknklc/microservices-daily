package com.dekankilic.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Loan", description = "Scheme to hold Loan information")
public class LoanDto {

    @Schema(
            description = "Mobile number of the customer",
            example = "9345432123"
    )
    @NotEmpty(message = "Mobile Number cannot be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Loan number of the customer",
            example = "548732457654"
    )
    @NotEmpty(message = "LoanNumber cannot be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Loan Number must be 12 digits")
    private String loanNumber;

    @Schema(
            description = "Type of the loan",
            example = "Home Loan"
    )
    @NotEmpty(message = "LoanType cannot be a null or empty")
    private String loanType;


    @Schema(
            description = "Total loan amount",
            example = "100000"
    )
    @Positive(message = "Total loan amount should be greater than zero")
    private int totalLoan;

    @Schema(
            description = "Total loan amount paid",
            example = "1000"
    )
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private int amountPaid;

    @Schema(
            description = "Total outstanding amount against a loan",
            example = "99000"
    )
    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    private int outstandingAmount;
}
