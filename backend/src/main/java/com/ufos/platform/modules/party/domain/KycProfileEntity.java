package com.ufos.platform.modules.party.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "kyc_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class KycProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id", unique = true, nullable = false)
    private UUID customerId;

    @Column(name = "kyc_status", nullable = false)
    private String kycStatus;

    @Column(name = "risk_score")
    private Double riskScore;

    @Column(name = "pep_flag", nullable = false)
    @Builder.Default
    private Boolean pepFlag = false;

    @Column(name = "sanctions_screening_status")
    private String sanctionsScreeningStatus;

    @Column(name = "adverse_media_status")
    private String adverse_media_status;

    @Column(name = "source_of_funds")
    private String sourceOfFunds;

    @Column(name = "source_of_wealth")
    private String sourceOfWealth;

    @Column(name = "expected_monthly_turnover")
    private java.math.BigDecimal expectedMonthlyTurnover;

    @Column(name = "expected_monthly_transaction_count")
    private Integer expectedMonthlyTransactionCount;

    @Column(name = "last_review_date")
    private LocalDate lastReviewDate;

    @Column(name = "next_review_date")
    private LocalDate nextReviewDate;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Version
    private Long version;
}
