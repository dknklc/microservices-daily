package com.dekankilic.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) { // I am going to pass multiple parameters to throw a detail exception to my end user.
        super(String.format("%s not found with the given input data %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
