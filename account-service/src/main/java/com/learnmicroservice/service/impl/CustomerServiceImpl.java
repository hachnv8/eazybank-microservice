package com.learnmicroservice.service.impl;

import com.learnmicroservice.dto.*;
import com.learnmicroservice.entity.Account;
import com.learnmicroservice.entity.Customer;
import com.learnmicroservice.exception.ResourceNotFoundException;
import com.learnmicroservice.mapper.AccountMapper;
import com.learnmicroservice.mapper.CustomerMapper;
import com.learnmicroservice.repository.AccountRepository;
import com.learnmicroservice.repository.CustomerRepository;
import com.learnmicroservice.service.CustomerService;
import com.learnmicroservice.service.client.CardFeignClient;
import com.learnmicroservice.service.client.LoanFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    @Qualifier("com.learnmicroservice.service.client.CardFeignClient")
    private final CardFeignClient cardFeignClient;
    @Qualifier("com.learnmicroservice.service.client.LoanFeignClient")
    private final LoanFeignClient loanFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loanFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if (loanDtoResponseEntity != null) {
            customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());
        }

        ResponseEntity<CardDto> cardDtoResponseEntity = cardFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if (cardDtoResponseEntity != null) {
            customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());
        }
        return customerDetailsDto;
    }
}
