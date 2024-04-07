package com.dekankilic.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts") // During the startup, Spring Boot will try to read all the properties with the prefix value as accounts, and it will try to map those property values to the fields present inside this class.
public record AccountContactInfoDto(
        String message,
        Map<String, String> contactDetails,
        List<String> onCallSupport
) {
}
