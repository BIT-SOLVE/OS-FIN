package com.ufos.platform.modules.party.dto;

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
public class KycProfileRequest {
    private String kycStatus;
    private Double riskScore;
    private Boolean pepFlag;
    private String sanctionsScreeningStatus;
    private String adverseMediaStatus;
    private String sourceOfFunds;
    private String sourceOfWealth;
    private BigDecimal expectedMonthlyTurnover;
    private Integer expectedMonthlyTransactionCount;
    private LocalDate nextReviewDate;
}
