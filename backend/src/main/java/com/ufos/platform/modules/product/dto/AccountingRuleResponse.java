package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class AccountingRuleResponse {
    private UUID id;
    private UUID productId;
    private String ruleCode;
    private String accountingEvent;
    private String debitGlPlaceholder;
    private String creditGlPlaceholder;
    private String description;
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
