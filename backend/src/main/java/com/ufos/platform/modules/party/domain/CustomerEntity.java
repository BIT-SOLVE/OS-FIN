package com.ufos.platform.modules.party.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_number", unique = true, nullable = false)
    private String customerNumber;

    @Column(name = "party_id", nullable = false)
    private UUID partyId;

    @Column(name = "legal_entity_id", nullable = false)
    private UUID legalEntityId;

    @Column(name = "branch_id")
    private UUID branchId;

    @Column(name = "customer_type", nullable = false)
    private String customerType;

    @Column(name = "customer_segment")
    private String customerSegment;

    @Column(name = "risk_rating", nullable = false)
    private String riskRating;

    @Column(name = "onboarding_status", nullable = false)
    private String onboardingStatus;

    @Column(name = "customer_status", nullable = false)
    private String customerStatus;

    @Column(name = "relationship_manager")
    private String relationshipManager;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

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
