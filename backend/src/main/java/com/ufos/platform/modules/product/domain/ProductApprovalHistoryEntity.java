package com.ufos.platform.modules.product.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_approval_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductApprovalHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String action;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    private String comment;

    @Column(name = "acted_by")
    private String actedBy;

    @Column(name = "acted_at", nullable = false)
    @Builder.Default
    private LocalDateTime actedAt = LocalDateTime.now();
}
