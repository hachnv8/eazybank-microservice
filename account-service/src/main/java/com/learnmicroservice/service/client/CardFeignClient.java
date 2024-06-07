package com.learnmicroservice.service.client;

import com.learnmicroservice.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "card-service", fallback = CardFallback.class)
public interface CardFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<CardDto> fetchCardDetails(
            @RequestHeader("eazybank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}