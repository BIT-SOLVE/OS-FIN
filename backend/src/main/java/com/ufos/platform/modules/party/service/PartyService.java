package com.ufos.platform.modules.party.service;

import com.ufos.platform.modules.party.domain.PartyEntity;
import com.ufos.platform.modules.party.dto.PartyRequest;
import com.ufos.platform.modules.party.dto.PartyResponse;
import com.ufos.platform.modules.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

    @Transactional
    public PartyResponse createParty(PartyRequest request) {
        String partyNumber = request.getPartyNumber();
        if (partyNumber == null || partyNumber.isBlank()) {
            partyNumber = generatePartyNumber();
        } else if (partyRepository.findByPartyNumber(partyNumber).isPresent()) {
            throw new IllegalArgumentException("Party with number " + partyNumber + " already exists");
        }

        PartyEntity entity = PartyEntity.builder()
                .partyNumber(partyNumber)
                .partyType(request.getPartyType())
                .displayName(request.getDisplayName())
                .legalName(request.getLegalName())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .incorporationDate(request.getIncorporationDate())
                .countryOfResidence(request.getCountryOfResidence())
                .countryOfIncorporation(request.getCountryOfIncorporation())
                .taxIdentifier(request.getTaxIdentifier())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .status(request.getStatus())
                .build();

        PartyEntity saved = partyRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public PartyResponse updateParty(UUID id, PartyRequest request) {
        PartyEntity entity = partyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Party not found with id " + id));

        entity.setDisplayName(request.getDisplayName());
        entity.setLegalName(request.getLegalName());
        entity.setFirstName(request.getFirstName());
        entity.setMiddleName(request.getMiddleName());
        entity.setLastName(request.getLastName());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setIncorporationDate(request.getIncorporationDate());
        entity.setCountryOfResidence(request.getCountryOfResidence());
        entity.setCountryOfIncorporation(request.getCountryOfIncorporation());
        entity.setTaxIdentifier(request.getTaxIdentifier());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setStatus(request.getStatus());

        PartyEntity saved = partyRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public PartyResponse getParty(UUID id) {
        return partyRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Party not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<PartyResponse> getAllParties() {
        return partyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private String generatePartyNumber() {
        String maxNumber = partyRepository.findMaxPartyNumber();
        if (maxNumber == null) {
            return "P000000001";
        }
        int next = Integer.parseInt(maxNumber.substring(1)) + 1;
        return String.format("P%09d", next);
    }

    private PartyResponse mapToResponse(PartyEntity entity) {
        return PartyResponse.builder()
                .id(entity.getId())
                .partyNumber(entity.getPartyNumber())
                .partyType(entity.getPartyType())
                .displayName(entity.getDisplayName())
                .legalName(entity.getLegalName())
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .incorporationDate(entity.getIncorporationDate())
                .countryOfResidence(entity.getCountryOfResidence())
                .countryOfIncorporation(entity.getCountryOfIncorporation())
                .taxIdentifier(entity.getTaxIdentifier())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
