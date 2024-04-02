package com.dekankilic.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountDto {

    @Schema(
            description = "Account Number of DekanBANK account",
            example = "1234567891"
    )
    @NotEmpty(message = "AccountNumber cannot be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of DekanBank account",
            example = "Savings"
    )
    @NotEmpty(message = "AccountType cannot be a null or empty")
    private String accountType;

    @Schema(
            description = "DekanBANK branch address",
            example = "123 NewYork"
    )
    @NotEmpty(message = "BranchAddress cannot be a null or empty")
    private String branchAddress;
}
