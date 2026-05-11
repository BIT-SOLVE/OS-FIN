package com.ufos.platform.modules.enterprise.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BusinessCalendarResponse {
    private UUID id;
    private String code;
    private String name;
    private String countryCode;
    private String currencyCode;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
