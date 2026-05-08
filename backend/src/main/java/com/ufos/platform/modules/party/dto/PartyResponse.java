package com.ufos.platform.modules.party.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PartyResponse {
    private UUID id;
    private String partyNumber;
    private String partyType;
    private String displayName;
    private String legalName;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate incorporationDate;
    private String countryOfResidence;
    private String countryOfIncorporation;
    private String taxIdentifier;
    private String email;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
