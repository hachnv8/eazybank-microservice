package com.learnmicroservice.service;

import com.learnmicroservice.dto.CustomerDetailsDto;

public interface CustomerService {
    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a give mobile number
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
