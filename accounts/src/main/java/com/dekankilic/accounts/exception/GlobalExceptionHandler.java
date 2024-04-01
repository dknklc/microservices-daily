package com.dekankilic.accounts.exception;

import com.dekankilic.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice // We are telling to the Spring Boot framework whenever an exception happens in any of my controller not only AccountController in future I may write different controller.
// In all such controllers, if any exception happens, please invoke a method that I am going to write inside this class.
public class GlobalExceptionHandler {

    // How Spring Boot framework will know that this method has to be invoked whenever there is an exception called CustomerAlreadyExistsException.
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
