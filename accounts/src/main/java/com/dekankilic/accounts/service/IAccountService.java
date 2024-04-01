package com.dekankilic.accounts.service;

import com.dekankilic.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);
}
