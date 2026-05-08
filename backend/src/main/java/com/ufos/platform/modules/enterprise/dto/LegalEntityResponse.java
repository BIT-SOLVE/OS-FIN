package com.ufos.platform.modules.enterprise.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LegalEntityResponse {
    private UUID id;
    private String code;
    private String name;
    private String legalName;
    private String registrationNumber;
    private String taxIdentifier;
    private String countryCode;
    private String baseCurrencyCode;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
