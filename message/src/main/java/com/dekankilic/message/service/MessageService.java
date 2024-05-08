package com.dekankilic.message.service;

import com.dekankilic.message.dto.AccountMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = "prod.accounts.placed", groupId = "message-group")
    public AccountMessageDto consumeAndSendEmail(AccountMessageDto accountMessageDto){
        log.info("Sending email with the details: " + accountMessageDto.toString());
        return accountMessageDto;
    }

}
