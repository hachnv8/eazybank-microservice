package com.learnmicroservice.service.client;

import com.learnmicroservice.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardFallback implements CardFeignClient {
    @Override
    public ResponseEntity<CardDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
