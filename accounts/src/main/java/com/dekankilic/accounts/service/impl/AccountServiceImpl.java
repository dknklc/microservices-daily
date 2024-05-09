package com.dekankilic.accounts.service.impl;

import com.dekankilic.accounts.constants.AccountConstants;
import com.dekankilic.accounts.dto.AccountDto;
import com.dekankilic.accounts.dto.AccountMessageDto;
import com.dekankilic.accounts.dto.CustomerDto;
import com.dekankilic.accounts.exception.CustomerAlreadyExistsException;
import com.dekankilic.accounts.exception.ResourceNotFoundException;
import com.dekankilic.accounts.mapper.AccountMapper;
import com.dekankilic.accounts.mapper.CustomerMapper;
import com.dekankilic.accounts.model.Account;
import com.dekankilic.accounts.model.Customer;
import com.dekankilic.accounts.repository.AccountRepository;
import com.dekankilic.accounts.repository.CustomerRepository;
import com.dekankilic.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final KafkaTemplate kafkaTemplate;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "+ customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        Account savedAccount = accountRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Account account, Customer customer){
        AccountMessageDto accountMsgDto = new AccountMessageDto(account.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
        log.info("Sending communication request for the details: {}", accountMsgDto);
        this.kafkaTemplate.send("prod.accounts.placed", String.valueOf(account.getAccountNumber()), accountMsgDto);
    }

    private Account createNewAccount(Customer customer){
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        Account newAccount = Account.builder()
                .accountNumber(randomAccNumber)
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.ADDRESS)
                .customerId(customer.getCustomerId())
                .build();

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto != null){
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString()));

            Account willbeSavedAccount = AccountMapper.mapToAccount(accountDto);
            willbeSavedAccount.setCustomerId(account.getCustomerId());
            account = accountRepository.save(willbeSavedAccount);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));

            Customer willbeSavedCustomer = CustomerMapper.mapToCustomer(customerDto);
            willbeSavedCustomer.setCustomerId(customer.getCustomerId());
            customerRepository.save(willbeSavedCustomer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}


















