package com.ufos.platform.modules.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_blocks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "block_reference", nullable = false, unique = true)
    private String blockReference;

    @Column(name = "block_amount", nullable = false)
    private BigDecimal blockAmount;

    @Column(name = "block_reason", nullable = false)
    private String blockReason;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "released_at")
    private LocalDateTime releasedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "released_by")
    private String releasedBy;
}
