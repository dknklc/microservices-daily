package com.dekankilic.accounts.service.client;

import com.dekankilic.accounts.dto.CardDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardFallback.class) // The value "cards" here is the same value where we have used to register with the Eureka Server
public interface CardFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardDto> fetchCardDetails(@RequestHeader("dekanbank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
