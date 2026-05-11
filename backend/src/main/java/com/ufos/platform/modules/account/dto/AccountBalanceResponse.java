package com.ufos.platform.modules.account.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccountBalanceResponse {
    private UUID id;
    private UUID accountId;
    private String currencyCode;
    private BigDecimal ledgerBalance;
    private BigDecimal availableBalance;
    private BigDecimal blockedBalance;
    private BigDecimal unclearedBalance;
    private BigDecimal overdraftAvailable;
    private LocalDateTime lastUpdatedAt;
}
