package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class InterestRuleResponse {
    private UUID id;
    private UUID productId;
    private String ruleCode;
    private String interestType;
    private String rateType;
    private BigDecimal fixedRate;
    private String indexCode;
    private BigDecimal spreadRate;
    private String calculationBasis;
    private String compoundingFrequency;
    private String accrualFrequency;
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
