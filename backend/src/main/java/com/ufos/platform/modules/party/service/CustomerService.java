package com.ufos.platform.modules.party.service;

import com.ufos.platform.modules.party.domain.CustomerEntity;
import com.ufos.platform.modules.party.domain.KycProfileEntity;
import com.ufos.platform.modules.party.dto.*;
import com.ufos.platform.modules.party.repository.CustomerRepository;
import com.ufos.platform.modules.party.repository.KycProfileRepository;
import com.ufos.platform.modules.enterprise.repository.LegalEntityRepository;
import com.ufos.platform.modules.enterprise.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final KycProfileRepository kycProfileRepository;
    private final PartyService partyService;
    private final LegalEntityRepository legalEntityRepository;
    private final BranchRepository branchRepository;

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        String customerNumber = request.getCustomerNumber();
        if (customerNumber == null || customerNumber.isBlank()) {
            customerNumber = generateCustomerNumber();
        } else if (customerRepository.findByCustomerNumber(customerNumber).isPresent()) {
            throw new IllegalArgumentException("Customer with number " + customerNumber + " already exists");
        }

        validateEnterpriseRefs(request.getLegalEntityId(), request.getBranchId());

        CustomerEntity entity = CustomerEntity.builder()
                .customerNumber(customerNumber)
                .partyId(request.getPartyId())
                .legalEntityId(request.getLegalEntityId())
                .branchId(request.getBranchId())
                .customerType(request.getCustomerType())
                .customerSegment(request.getCustomerSegment())
                .riskRating(request.getRiskRating())
                .onboardingStatus(request.getOnboardingStatus())
                .customerStatus(request.getCustomerStatus())
                .relationshipManager(request.getRelationshipManager())
                .build();

        CustomerEntity saved = customerRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public CustomerResponse onboardCustomer(CustomerOnboardingRequest request) {
        PartyResponse party = partyService.createParty(request.getParty());

        CustomerRequest customerReq = request.getCustomer();
        customerReq.setPartyId(party.getId());
        CustomerResponse customer = createCustomer(customerReq);

        if (request.getKyc() != null) {
            KycProfileEntity kyc = KycProfileEntity.builder()
                    .customerId(customer.getId())
                    .kycStatus(request.getKyc().getKycStatus())
                    .riskScore(request.getKyc().getRiskScore())
                    .pepFlag(request.getKyc().getPepFlag() != null ? request.getKyc().getPepFlag() : false)
                    .sanctionsScreeningStatus(request.getKyc().getSanctionsScreeningStatus())
                    .adverse_media_status(request.getKyc().getAdverseMediaStatus())
                    .sourceOfFunds(request.getKyc().getSourceOfFunds())
                    .sourceOfWealth(request.getKyc().getSourceOfWealth())
                    .expectedMonthlyTurnover(request.getKyc().getExpectedMonthlyTurnover())
                    .expectedMonthlyTransactionCount(request.getKyc().getExpectedMonthlyTransactionCount())
                    .nextReviewDate(request.getKyc().getNextReviewDate())
                    .build();
            kycProfileRepository.save(kyc);
        }

        return customer;
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(UUID id) {
        return customerRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void validateEnterpriseRefs(UUID leId, UUID branchId) {
        if (!legalEntityRepository.existsById(leId)) {
            throw new IllegalArgumentException("Legal entity not found with id " + leId);
        }
        if (branchId != null && !branchRepository.existsById(branchId)) {
            throw new IllegalArgumentException("Branch not found with id " + branchId);
        }
    }

    private String generateCustomerNumber() {
        String maxNumber = customerRepository.findMaxCustomerNumber();
        if (maxNumber == null) {
            return "C000000001";
        }
        int next = Integer.parseInt(maxNumber.substring(1)) + 1;
        return String.format("C%09d", next);
    }

    private CustomerResponse mapToResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .customerNumber(entity.getCustomerNumber())
                .partyId(entity.getPartyId())
                .legalEntityId(entity.getLegalEntityId())
                .branchId(entity.getBranchId())
                .customerType(entity.getCustomerType())
                .customerSegment(entity.getCustomerSegment())
                .riskRating(entity.getRiskRating())
                .onboardingStatus(entity.getOnboardingStatus())
                .customerStatus(entity.getCustomerStatus())
                .relationshipManager(entity.getRelationshipManager())
                .openedAt(entity.getOpenedAt())
                .closedAt(entity.getClosedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
