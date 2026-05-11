package com.ufos.platform.modules.iam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleResponse {
    private UUID id;
    private String code;
    private String name;
    private String description;
}
