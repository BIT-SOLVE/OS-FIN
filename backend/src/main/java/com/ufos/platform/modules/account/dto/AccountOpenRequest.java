package com.ufos.platform.modules.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountOpenRequest {
    private String accountNumber;
    @NotNull
    private UUID customerId;
    @NotNull
    private UUID legalEntityId;
    private UUID branchId;
    @NotNull
    private UUID productId;
    @NotBlank
    private String currencyCode;
    @NotBlank
    private String accountType;
    @NotBlank
    private String accountName;
    @Builder.Default
    private BigDecimal overdraftLimit = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal minimumBalance = BigDecimal.ZERO;
}
