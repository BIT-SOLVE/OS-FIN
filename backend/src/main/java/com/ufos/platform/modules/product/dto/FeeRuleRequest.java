package com.ufos.platform.modules.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeRuleRequest {
    @NotBlank
    private String ruleCode;
    @NotBlank
    private String feeType;
    private BigDecimal feeAmount;
    private BigDecimal feeRate;
    private String currencyCode;
    private String chargeEvent;
    private String calculationMethod;
    @NotBlank
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
