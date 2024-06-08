package com.learnmicroservice.service.client;

import com.learnmicroservice.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanFallback implements LoanFeignClient{
    @Override
    public ResponseEntity<LoanDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
