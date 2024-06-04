package com.learnmicroservice.service.client;

import com.learnmicroservice.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loan-service")
public interface LoanFeignClient {
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    ResponseEntity<LoanDto> fetchLoanDetails(@RequestParam String mobileNumber);
}