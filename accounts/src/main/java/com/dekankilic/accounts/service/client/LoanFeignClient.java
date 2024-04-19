package com.dekankilic.accounts.service.client;

import com.dekankilic.accounts.dto.LoanDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoanFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoanDto> fetchLoanDetails(@RequestHeader("dekanbank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}