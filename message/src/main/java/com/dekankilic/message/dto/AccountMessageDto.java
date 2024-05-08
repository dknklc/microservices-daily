package com.dekankilic.message.dto;

public record AccountMessageDto(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
) {
}
