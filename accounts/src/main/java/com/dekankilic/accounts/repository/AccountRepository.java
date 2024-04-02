package com.dekankilic.accounts.repository;

import com.dekankilic.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(Long customerId);

    @Transactional
    @Modifying // will tell Spring Data JPA framework that this method is going to modify the data.So that is why please execute the query of this method inside a Transaction.
    void deleteByCustomerId(Long customerId);
}
