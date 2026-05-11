package com.ufos.platform.modules.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interest_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class InterestRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "rule_code", nullable = false)
    private String ruleCode;

    @Column(name = "interest_type", nullable = false)
    private String interestType;

    @Column(name = "rate_type", nullable = false)
    private String rateType;

    @Column(name = "fixed_rate")
    private BigDecimal fixedRate;

    @Column(name = "index_code")
    private String indexCode;

    @Column(name = "spread_rate")
    private BigDecimal spreadRate;

    @Column(name = "calculation_basis")
    private String calculationBasis;

    @Column(name = "compounding_frequency")
    private String compoundingFrequency;

    @Column(name = "accrual_frequency")
    private String accrualFrequency;

    @Column(nullable = false)
    private String status;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

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
