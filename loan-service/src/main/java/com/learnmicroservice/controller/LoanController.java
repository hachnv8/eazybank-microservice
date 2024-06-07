package com.learnmicroservice.controller;

import com.learnmicroservice.constant.LoanConstant;
import com.learnmicroservice.dto.LoanContactInfoDto;
import com.learnmicroservice.dto.LoanDto;
import com.learnmicroservice.dto.ResponseDto;
import com.learnmicroservice.service.impl.LoanServiceImpl;
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
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Slf4j
public class LoanController {

    private final LoanServiceImpl loanService;
    private final LoanContactInfoDto loanContactInfoDto;
    private final Environment env;

    @Value("${build.version}")
    private String buildVersion;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber
    ) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstant.STATUS_201, LoanConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> fetchLoanDetails(
            @RequestHeader("eazybank-correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber
    ) {
        log.debug("Fetching loan for correlation id {}", correlationId);
        LoanDto LoanDto = loanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(LoanDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto LoanDto) {
        boolean isUpdated = loanService.updateLoan(LoanDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstant.STATUS_200, LoanConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstant.STATUS_417, LoanConstant.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber
    ) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstant.STATUS_200, LoanConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstant.STATUS_417, LoanConstant.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(env.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoanContactInfoDto> getContactInfo() {
        log.debug("Invoked Loan contact-info API");
        throw new RuntimeException();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(loanContactInfoDto);
    }

}
