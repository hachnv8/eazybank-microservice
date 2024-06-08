package com.learnmicroservice.controller;

import com.learnmicroservice.constant.AccountConstant;
import com.learnmicroservice.dto.AccountContactInfoDto;
import com.learnmicroservice.dto.CustomerDto;
import com.learnmicroservice.dto.ResponseDto;
import com.learnmicroservice.service.AccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final Environment env;
    private final AccountContactInfoDto accountContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;


    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstant.STATUS_500, AccountConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstant.STATUS_500, AccountConstant.MESSAGE_500));
        }
    }

    @Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        log.debug("getBuildInfo() method Invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        log.debug("getBuildInfoFallback() method Invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }

    @RateLimiter(name= "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(env.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 17");
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }
}
