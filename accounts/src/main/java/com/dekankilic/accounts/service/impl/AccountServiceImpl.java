package com.dekankilic.accounts.service.impl;

import com.dekankilic.accounts.constants.AccountConstants;
import com.dekankilic.accounts.dto.CustomerDto;
import com.dekankilic.accounts.exception.CustomerAlreadyExistsException;
import com.dekankilic.accounts.mapper.CustomerMapper;
import com.dekankilic.accounts.model.Account;
import com.dekankilic.accounts.model.Customer;
import com.dekankilic.accounts.repository.AccountRepository;
import com.dekankilic.accounts.repository.CustomerRepository;
import com.dekankilic.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "+ customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer){
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        Account newAccount = Account.builder()
                .accountNumber(randomAccNumber)
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.ADDRESS)
                .customerId(customer.getCustomerId())
                .build();

        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
}
