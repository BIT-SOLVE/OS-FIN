package com.ufos.platform.modules.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_lifecycle_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLifecycleHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private String action;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    private String reason;

    @Column(name = "acted_by")
    private String actedBy;

    @Column(name = "acted_at", nullable = false)
    @Builder.Default
    private LocalDateTime actedAt = LocalDateTime.now();
}
