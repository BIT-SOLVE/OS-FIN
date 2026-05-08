package com.ufos.platform.modules.party.service;

import com.ufos.platform.modules.party.domain.KycProfileEntity;
import com.ufos.platform.modules.party.dto.KycProfileRequest;
import com.ufos.platform.modules.party.dto.KycProfileResponse;
import com.ufos.platform.modules.party.repository.CustomerRepository;
import com.ufos.platform.modules.party.repository.KycProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KycProfileService {

    private final KycProfileRepository kycProfileRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public KycProfileResponse updateKycProfile(UUID customerId, KycProfileRequest request) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found with id " + customerId);
        }

        KycProfileEntity entity = kycProfileRepository.findByCustomerId(customerId)
                .orElseGet(() -> KycProfileEntity.builder().customerId(customerId).build());

        entity.setKycStatus(request.getKycStatus());
        entity.setRiskScore(request.getRiskScore());
        entity.setPepFlag(request.getPepFlag() != null ? request.getPepFlag() : false);
        entity.setSanctionsScreeningStatus(request.getSanctionsScreeningStatus());
        entity.setAdverse_media_status(request.getAdverseMediaStatus());
        entity.setSourceOfFunds(request.getSourceOfFunds());
        entity.setSourceOfWealth(request.getSourceOfWealth());
        entity.setExpectedMonthlyTurnover(request.getExpectedMonthlyTurnover());
        entity.setExpectedMonthlyTransactionCount(request.getExpectedMonthlyTransactionCount());
        entity.setNextReviewDate(request.getNextReviewDate());

        KycProfileEntity saved = kycProfileRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public KycProfileResponse approveKyc(UUID customerId, String approvedBy) {
        KycProfileEntity entity = kycProfileRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("KYC profile not found for customer " + customerId));

        entity.setKycStatus("APPROVED");
        entity.setApprovedBy(approvedBy);
        entity.setApprovedAt(LocalDateTime.now());

        KycProfileEntity saved = kycProfileRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public KycProfileResponse rejectKyc(UUID customerId, String rejectionReason, String rejectedBy) {
        if (rejectionReason == null || rejectionReason.isBlank()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }

        KycProfileEntity entity = kycProfileRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("KYC profile not found for customer " + customerId));

        entity.setKycStatus("REJECTED");
        entity.setRejectionReason(rejectionReason);
        entity.setApprovedBy(rejectedBy);
        entity.setApprovedAt(LocalDateTime.now());

        KycProfileEntity saved = kycProfileRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public KycProfileResponse getKycProfile(UUID customerId) {
        return kycProfileRepository.findByCustomerId(customerId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("KYC profile not found for customer " + customerId));
    }

    private KycProfileResponse mapToResponse(KycProfileEntity entity) {
        return KycProfileResponse.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .kycStatus(entity.getKycStatus())
                .riskScore(entity.getRiskScore())
                .pepFlag(entity.getPepFlag())
                .sanctionsScreeningStatus(entity.getSanctionsScreeningStatus())
                .adverseMediaStatus(entity.getAdverse_media_status())
                .sourceOfFunds(entity.getSourceOfFunds())
                .sourceOfWealth(entity.getSourceOfWealth())
                .expectedMonthlyTurnover(entity.getExpectedMonthlyTurnover())
                .expectedMonthlyTransactionCount(entity.getExpectedMonthlyTransactionCount())
                .lastReviewDate(entity.getLastReviewDate())
                .nextReviewDate(entity.getNextReviewDate())
                .approvedBy(entity.getApprovedBy())
                .approvedAt(entity.getApprovedAt())
                .rejectionReason(entity.getRejectionReason())
                .build();
    }
}
