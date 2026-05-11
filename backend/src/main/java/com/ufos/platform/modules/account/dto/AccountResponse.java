package com.ufos.platform.modules.account.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccountResponse {
    private UUID id;
    private String accountNumber;
    private UUID customerId;
    private UUID partyId;
    private UUID legalEntityId;
    private UUID branchId;
    private UUID productId;
    private String currencyCode;
    private String accountType;
    private String accountName;
    private String accountStatus;
    private String lifecycleStatus;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private BigDecimal overdraftLimit;
    private BigDecimal minimumBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
