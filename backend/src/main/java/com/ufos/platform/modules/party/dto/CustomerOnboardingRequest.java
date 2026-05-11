package com.ufos.platform.modules.party.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOnboardingRequest {
    @NotNull
    @Valid
    private PartyRequest party;

    @NotNull
    @Valid
    private CustomerRequest customer;

    private KycProfileRequest kyc;
}
