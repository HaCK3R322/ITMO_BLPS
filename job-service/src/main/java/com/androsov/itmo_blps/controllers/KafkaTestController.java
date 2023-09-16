package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.servicies.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class KafkaTestController {
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/send-message")
    public String sendMessageToKafka() {
        kafkaProducerService.sendMessage("Hello, World!");
        return "Message sent to Kafka!";
    }
}
