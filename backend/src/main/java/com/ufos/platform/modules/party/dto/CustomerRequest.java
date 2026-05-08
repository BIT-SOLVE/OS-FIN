package com.ufos.platform.modules.party.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String customerNumber;

    @NotNull
    private UUID partyId;

    @NotNull
    private UUID legalEntityId;

    private UUID branchId;

    @NotBlank
    private String customerType;

    private String customerSegment;

    @NotBlank
    private String riskRating;

    @NotBlank
    private String onboardingStatus;

    @NotBlank
    private String customerStatus;

    private String relationshipManager;
}
