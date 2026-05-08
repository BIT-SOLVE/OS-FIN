package com.ufos.platform.modules.enterprise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequest {
    @NotBlank
    @Size(max = 50)
    private String code;

    @NotBlank
    private String name;

    private String branchType;

    @NotBlank
    @Size(min = 2, max = 2)
    private String countryCode;

    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;

    @NotBlank
    private String status;
}
