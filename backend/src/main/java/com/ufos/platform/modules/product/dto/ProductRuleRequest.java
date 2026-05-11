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
public class ProductRuleRequest {
    @NotBlank
    private String ruleType;
    @NotBlank
    private String ruleCode;
    @NotBlank
    private String ruleName;
    private String ruleValue;
    private String ruleJson;
    @NotBlank
    private String status;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
