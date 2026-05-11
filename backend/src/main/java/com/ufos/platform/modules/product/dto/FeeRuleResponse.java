package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class FeeRuleResponse {
    private UUID id;
    private UUID productId;
    private String ruleCode;
    private String feeType;
    private BigDecimal feeAmount;
    private BigDecimal feeRate;
    private String currencyCode;
    private String chargeEvent;
    private String calculationMethod;
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
