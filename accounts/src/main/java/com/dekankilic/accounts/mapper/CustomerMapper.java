package com.dekankilic.accounts.mapper;

import com.dekankilic.accounts.dto.CustomerDto;
import com.dekankilic.accounts.model.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer){
        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }

    public static Customer mapToCustomer(CustomerDto customerDto){
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .build();
    }
}
