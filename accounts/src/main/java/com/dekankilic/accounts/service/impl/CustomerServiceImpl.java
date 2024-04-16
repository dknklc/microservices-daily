package com.dekankilic.accounts.service.impl;

import com.dekankilic.accounts.dto.CardDto;
import com.dekankilic.accounts.dto.CustomerDetailsDto;
import com.dekankilic.accounts.dto.CustomerDto;
import com.dekankilic.accounts.dto.LoanDto;
import com.dekankilic.accounts.exception.ResourceNotFoundException;
import com.dekankilic.accounts.mapper.AccountMapper;
import com.dekankilic.accounts.mapper.CustomerMapper;
import com.dekankilic.accounts.model.Account;
import com.dekankilic.accounts.model.Customer;
import com.dekankilic.accounts.repository.AccountRepository;
import com.dekankilic.accounts.repository.CustomerRepository;
import com.dekankilic.accounts.service.ICustomerService;
import com.dekankilic.accounts.service.client.CardFeignClient;
import com.dekankilic.accounts.service.client.LoanFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final LoanFeignClient loanFeignClient;
    private final CardFeignClient cardFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer);
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loanFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());
        ResponseEntity<CardDto> cardDtoResponseEntity = cardFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
