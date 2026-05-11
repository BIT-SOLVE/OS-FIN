package com.ufos.platform.modules.party.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyRequest {
    private String partyNumber;

    @NotBlank
    private String partyType;

    @NotBlank
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

    @NotBlank
    private String status;
}
