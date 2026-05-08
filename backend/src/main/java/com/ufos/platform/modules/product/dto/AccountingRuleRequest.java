package com.ufos.platform.modules.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountingRuleRequest {
    @NotBlank
    private String ruleCode;
    @NotBlank
    private String accountingEvent;
    private String debitGlPlaceholder;
    private String creditGlPlaceholder;
    private String description;
    @NotBlank
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
