package com.dekankilic.loans.service;

import com.dekankilic.loans.dto.LoanDto;

public interface ILoanService {

    /**
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber
     * @return
     */
    LoanDto fetchLoan(String mobileNumber);

    /**
     * @param loanDto
     * @return
     */
    boolean updateLoan(LoanDto loanDto);

    /**
     * @param mobileNumber
     * @return
     */
    boolean deleteLoan(String mobileNumber);

}
