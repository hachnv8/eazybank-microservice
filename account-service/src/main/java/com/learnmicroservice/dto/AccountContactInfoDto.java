package com.learnmicroservice.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "account")
public record AccountContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) implements Serializable {
}
