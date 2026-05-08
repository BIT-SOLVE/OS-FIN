package com.ufos.platform.modules.enterprise.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BranchResponse {
    private UUID id;
    private UUID legalEntityId;
    private String code;
    private String name;
    private String branchType;
    private String countryCode;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
