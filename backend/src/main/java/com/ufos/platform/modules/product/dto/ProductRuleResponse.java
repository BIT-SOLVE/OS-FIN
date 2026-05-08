package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class ProductRuleResponse {
    private UUID id;
    private UUID productId;
    private String ruleType;
    private String ruleCode;
    private String ruleName;
    private String ruleValue;
    private String ruleJson;
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
