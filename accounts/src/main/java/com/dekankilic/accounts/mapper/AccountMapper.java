package com.dekankilic.accounts.mapper;

import com.dekankilic.accounts.dto.AccountDto;
import com.dekankilic.accounts.model.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account account){
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }

    public static Account mapToAccount(AccountDto accountDto){
        return Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .accountType(accountDto.getAccountType())
                .branchAddress(accountDto.getBranchAddress())
                .build();
    }
}
