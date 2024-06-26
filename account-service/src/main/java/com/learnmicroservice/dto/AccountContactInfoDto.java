package com.learnmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "account")
public class AccountContactInfoDto implements Serializable {
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
