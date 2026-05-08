package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductTypeResponse {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private String productFamily;
    private String status;
}
