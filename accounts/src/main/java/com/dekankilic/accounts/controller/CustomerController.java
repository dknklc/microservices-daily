package com.dekankilic.accounts.controller;

import com.dekankilic.accounts.dto.CustomerDetailsDto;
import com.dekankilic.accounts.dto.ErrorResponseDto;
import com.dekankilic.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated // tell Spring Boot framework to perform validations on all these REST APIs that I have defined inside the AccountController.
@Tag(name = "CRUD REST APIs for Customers in DEKANBANK", description = "CRUD REST APIs in DEKANBANK to FETCH Customer Details")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final ICustomerService iCustomerService;

    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    @Operation(
            method = "GET",
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch a Customer details based on mobile number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CustomerDetailsDto.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("dekanbank-correlation-id") String correlationId,
                                                                   @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
        logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetails method end");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetailsDto);
    }

}














