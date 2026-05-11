package com.ufos.platform.modules.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_balances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "account_id", nullable = false, unique = true)
    private UUID accountId;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "ledger_balance", nullable = false)
    @Builder.Default
    private BigDecimal ledgerBalance = BigDecimal.ZERO;

    @Column(name = "available_balance", nullable = false)
    @Builder.Default
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Column(name = "blocked_balance", nullable = false)
    @Builder.Default
    private BigDecimal blockedBalance = BigDecimal.ZERO;

    @Column(name = "uncleared_balance", nullable = false)
    @Builder.Default
    private BigDecimal unclearedBalance = BigDecimal.ZERO;

    @Column(name = "overdraft_available", nullable = false)
    @Builder.Default
    private BigDecimal overdraftAvailable = BigDecimal.ZERO;

    @Column(name = "last_updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime lastUpdatedAt = LocalDateTime.now();

    @Version
    private Long version;
}
