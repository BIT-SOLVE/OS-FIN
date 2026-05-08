package com.ufos.platform.modules.enterprise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityRequest {
    @NotBlank
    @Size(max = 50)
    private String code;

    @NotBlank
    private String name;

    private String legalName;
    private String registrationNumber;
    private String taxIdentifier;

    @NotBlank
    @Size(min = 2, max = 2)
    private String countryCode;

    @NotBlank
    @Size(min = 3, max = 3)
    private String baseCurrencyCode;

    @NotBlank
    private String status;
}
