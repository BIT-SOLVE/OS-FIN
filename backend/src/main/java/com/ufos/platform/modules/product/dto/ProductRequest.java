package com.ufos.platform.modules.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank
    private String productCode;
    @NotBlank
    private String productName;
    @NotNull
    private UUID productTypeId;
    private UUID legalEntityId;
    private String currencyCode;
    @NotBlank
    private String productFamily;
    private String productCategory;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private String description;
}
