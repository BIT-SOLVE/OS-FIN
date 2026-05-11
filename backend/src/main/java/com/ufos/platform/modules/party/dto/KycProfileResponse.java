package com.ufos.platform.modules.party.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class KycProfileResponse {
    private UUID id;
    private UUID customerId;
    private String kycStatus;
    private Double riskScore;
    private Boolean pepFlag;
    private String sanctionsScreeningStatus;
    private String adverseMediaStatus;
    private String sourceOfFunds;
    private String sourceOfWealth;
    private BigDecimal expectedMonthlyTurnover;
    private Integer expectedMonthlyTransactionCount;
    private LocalDate lastReviewDate;
    private LocalDate nextReviewDate;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String rejectionReason;
}
