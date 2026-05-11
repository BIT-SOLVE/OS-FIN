package com.ufos.platform.modules.enterprise.service;

import com.ufos.platform.modules.enterprise.domain.BranchEntity;
import com.ufos.platform.modules.enterprise.dto.BranchRequest;
import com.ufos.platform.modules.enterprise.dto.BranchResponse;
import com.ufos.platform.modules.enterprise.repository.BranchRepository;
import com.ufos.platform.modules.enterprise.repository.LegalEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final LegalEntityRepository legalEntityRepository;

    @Transactional
    public BranchResponse createBranch(UUID legalEntityId, BranchRequest request) {
        if (!legalEntityRepository.existsById(legalEntityId)) {
            throw new IllegalArgumentException("Legal entity not found with id " + legalEntityId);
        }

        if (branchRepository.findByLegalEntityIdAndCode(legalEntityId, request.getCode().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Branch with code " + request.getCode() + " already exists for this legal entity");
        }

        BranchEntity entity = BranchEntity.builder()
                .legalEntityId(legalEntityId)
                .code(request.getCode().toUpperCase())
                .name(request.getName())
                .branchType(request.getBranchType())
                .countryCode(request.getCountryCode().toUpperCase())
                .city(request.getCity())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .postalCode(request.getPostalCode())
                .status(request.getStatus())
                .build();

        BranchEntity saved = branchRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public BranchResponse updateBranch(UUID id, BranchRequest request) {
        BranchEntity entity = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id " + id));

        entity.setName(request.getName());
        entity.setBranchType(request.getBranchType());
        entity.setCountryCode(request.getCountryCode().toUpperCase());
        entity.setCity(request.getCity());
        entity.setAddressLine1(request.getAddressLine1());
        entity.setAddressLine2(request.getAddressLine2());
        entity.setPostalCode(request.getPostalCode());
        entity.setStatus(request.getStatus());

        BranchEntity saved = branchRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public BranchResponse getBranch(UUID id) {
        return branchRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<BranchResponse> getBranchesByLegalEntity(UUID legalEntityId) {
        return branchRepository.findByLegalEntityId(legalEntityId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BranchResponse mapToResponse(BranchEntity entity) {
        return BranchResponse.builder()
                .id(entity.getId())
                .legalEntityId(entity.getLegalEntityId())
                .code(entity.getCode())
                .name(entity.getName())
                .branchType(entity.getBranchType())
                .countryCode(entity.getCountryCode())
                .city(entity.getCity())
                .addressLine1(entity.getAddressLine1())
                .addressLine2(entity.getAddressLine2())
                .postalCode(entity.getPostalCode())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
