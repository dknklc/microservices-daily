package com.dekankilic.loans.service.impl;

import com.dekankilic.loans.constants.LoanConstants;
import com.dekankilic.loans.dto.LoanDto;
import com.dekankilic.loans.exception.LoanAlreadyExistsException;
import com.dekankilic.loans.exception.ResourceNotFoundException;
import com.dekankilic.loans.mapper.LoanMapper;
import com.dekankilic.loans.model.Loan;
import com.dekankilic.loans.repository.LoanRepository;
import com.dekankilic.loans.service.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService {
    private final LoanRepository loanRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if(optionalLoan.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " +mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    private Loan createNewLoan(String mobileNumber){
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        return Loan.builder()
                .mobileNumber(mobileNumber)
                .loanNumber(Long.toString(randomLoanNumber))
                .loanType(LoanConstants.HOME_LOAN)
                .totalLoan(LoanConstants.NEW_LOAN_LIMIT)
                .amountPaid(0)
                .outstandingAmount(LoanConstants.NEW_LOAN_LIMIT)
                .build();
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        return LoanMapper.mapToLoanDto(loan);
    }


    // Burayı düzelt !!!
    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(() -> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));
        Loan willBeUpdated = LoanMapper.mapToLoan(loanDto);
        willBeUpdated.setLoanId(loan.getLoanId());
        willBeUpdated.setLoanNumber(loan.getLoanNumber());
        loanRepository.save(willBeUpdated);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        loanRepository.deleteById(loan.getLoanId());
        return true;
    }
}
