package com.ufos.platform.modules.party.api;

import com.ufos.platform.modules.party.dto.KycProfileRequest;
import com.ufos.platform.modules.party.dto.KycProfileResponse;
import com.ufos.platform.modules.party.service.KycProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/party/customers/{customerId}/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycProfileService kycProfileService;

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR')")
    public KycProfileResponse getKycProfile(@PathVariable UUID customerId) {
        return kycProfileService.getKycProfile(customerId);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public KycProfileResponse updateKycProfile(@PathVariable UUID customerId, @Valid @RequestBody KycProfileRequest request) {
        return kycProfileService.updateKycProfile(customerId, request);
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public KycProfileResponse approveKyc(@PathVariable UUID customerId, Authentication authentication) {
        return kycProfileService.approveKyc(customerId, authentication.getName());
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public KycProfileResponse rejectKyc(@PathVariable UUID customerId, @RequestParam String reason, Authentication authentication) {
        return kycProfileService.rejectKyc(customerId, reason, authentication.getName());
    }
}
