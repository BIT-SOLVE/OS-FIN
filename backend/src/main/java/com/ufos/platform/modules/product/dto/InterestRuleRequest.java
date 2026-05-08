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
public class InterestRuleRequest {
    @NotBlank
    private String ruleCode;
    @NotBlank
    private String interestType;
    @NotBlank
    private String rateType;
    private BigDecimal fixedRate;
    private String indexCode;
    private BigDecimal spreadRate;
    private String calculationBasis;
    private String compoundingFrequency;
    private String accrualFrequency;
    @NotBlank
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
