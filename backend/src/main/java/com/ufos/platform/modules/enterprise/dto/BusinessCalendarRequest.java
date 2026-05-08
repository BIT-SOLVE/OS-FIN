package com.ufos.platform.modules.enterprise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCalendarRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String countryCode;
    private String currencyCode;
    private String description;
    @NotBlank
    private String status;
}
