package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductResponse {
    private UUID id;
    private String productCode;
    private String productName;
    private UUID productTypeId;
    private UUID legalEntityId;
    private String currencyCode;
    private String productFamily;
    private String productCategory;
    private String lifecycleStatus;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
