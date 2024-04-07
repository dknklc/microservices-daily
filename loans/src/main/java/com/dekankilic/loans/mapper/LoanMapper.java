package com.dekankilic.loans.mapper;

import com.dekankilic.loans.dto.LoanDto;
import com.dekankilic.loans.model.Loan;

public class LoanMapper {

    public static LoanDto mapToLoanDto(Loan loan){
        return LoanDto.builder()
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .mobileNumber(loan.getMobileNumber())
                .totalLoan(loan.getTotalLoan())
                .amountPaid(loan.getAmountPaid())
                .outstandingAmount(loan.getOutstandingAmount())
                .build();
    }

    public static Loan mapToLoan(LoanDto loanDto){
        return Loan.builder()
                .loanNumber(loanDto.getLoanNumber())
                .loanType(loanDto.getLoanType())
                .mobileNumber(loanDto.getMobileNumber())
                .totalLoan(loanDto.getTotalLoan())
                .amountPaid(loanDto.getAmountPaid())
                .outstandingAmount(loanDto.getOutstandingAmount())
                .build();
    }
}
