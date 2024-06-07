package com.learnmicroservice.service.client;

import com.learnmicroservice.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loan-service", fallback = LoanFallback.class)
public interface LoanFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<LoanDto> fetchLoanDetails(
            @RequestHeader("eazybank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}
