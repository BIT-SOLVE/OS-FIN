package com.ufos.platform.modules.enterprise.service;

import com.ufos.platform.modules.enterprise.domain.LegalEntityEntity;
import com.ufos.platform.modules.enterprise.dto.LegalEntityRequest;
import com.ufos.platform.modules.enterprise.dto.LegalEntityResponse;
import com.ufos.platform.modules.enterprise.repository.LegalEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LegalEntityService {

    private final LegalEntityRepository legalEntityRepository;

    @Transactional
    public LegalEntityResponse createLegalEntity(LegalEntityRequest request) {
        if (legalEntityRepository.findByCode(request.getCode().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Legal entity with code " + request.getCode() + " already exists");
        }

        LegalEntityEntity entity = LegalEntityEntity.builder()
                .code(request.getCode().toUpperCase())
                .name(request.getName())
                .legalName(request.getLegalName())
                .registrationNumber(request.getRegistrationNumber())
                .taxIdentifier(request.getTaxIdentifier())
                .countryCode(request.getCountryCode().toUpperCase())
                .baseCurrencyCode(request.getBaseCurrencyCode().toUpperCase())
                .status(request.getStatus())
                .build();

        LegalEntityEntity saved = legalEntityRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public LegalEntityResponse updateLegalEntity(UUID id, LegalEntityRequest request) {
        LegalEntityEntity entity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Legal entity not found with id " + id));

        entity.setName(request.getName());
        entity.setLegalName(request.getLegalName());
        entity.setRegistrationNumber(request.getRegistrationNumber());
        entity.setTaxIdentifier(request.getTaxIdentifier());
        entity.setCountryCode(request.getCountryCode().toUpperCase());
        entity.setBaseCurrencyCode(request.getBaseCurrencyCode().toUpperCase());
        entity.setStatus(request.getStatus());

        LegalEntityEntity saved = legalEntityRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public LegalEntityResponse getLegalEntity(UUID id) {
        return legalEntityRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Legal entity not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<LegalEntityResponse> getAllLegalEntities() {
        return legalEntityRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LegalEntityResponse mapToResponse(LegalEntityEntity entity) {
        return LegalEntityResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .legalName(entity.getLegalName())
                .registrationNumber(entity.getRegistrationNumber())
                .taxIdentifier(entity.getTaxIdentifier())
                .countryCode(entity.getCountryCode())
                .baseCurrencyCode(entity.getBaseCurrencyCode())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
