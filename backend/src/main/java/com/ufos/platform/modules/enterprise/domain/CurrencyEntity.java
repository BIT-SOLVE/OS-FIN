package com.ufos.platform.modules.enterprise.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "currencies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CurrencyEntity {

    @Id
    @Column(length = 3)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "numeric_code", length = 3)
    private String numericCode;

    @Column(name = "minor_units", nullable = false)
    @Builder.Default
    private Integer minorUnits = 2;

    @Column(nullable = false)
    private String status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
