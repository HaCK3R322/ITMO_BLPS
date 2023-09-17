package com.androsov.moderationservice.services;

import com.androsov.moderationservice.dto.messages.UserToCheckMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaUserCheckConsumer {
    private final UserCheckService userCheckService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-check", groupId = "user-check-group")
    public void consumeUserToCheckMessage(String messageJson) throws JsonProcessingException {
        UserToCheckMessage message = objectMapper.readValue(messageJson, UserToCheckMessage.class);

        userCheckService.checkUser(message);
    }
}
