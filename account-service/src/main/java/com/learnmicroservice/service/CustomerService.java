package com.learnmicroservice.service;

import com.learnmicroservice.dto.CustomerDetailsDto;

public interface CustomerService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
