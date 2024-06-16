package com.hacheery.function;

import com.hacheery.dto.AccountMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class MessageFunction {
    @Bean
    public Function<AccountMsgDto, AccountMsgDto> email() {
        return accountMsgDto -> {
            log.info("Sending email with the details: {}", accountMsgDto.toString());
            return accountMsgDto;
        };
    }

    @Bean
    public Function<AccountMsgDto, Long> sms() {
        return accountMsgDto -> {
            log.info("Sending sms with the details: {}", accountMsgDto.toString());
            return accountMsgDto.accountNumber();
        };
    }
}
