package com.ufos.platform.modules.party.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerResponse {
    private UUID id;
    private String customerNumber;
    private UUID partyId;
    private UUID legalEntityId;
    private UUID branchId;
    private String customerType;
    private String customerSegment;
    private String riskRating;
    private String onboardingStatus;
    private String customerStatus;
    private String relationshipManager;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
