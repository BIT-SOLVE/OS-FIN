package com.ufos.platform.modules.enterprise.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyResponse {
    private String code;
    private String name;
    private String numericCode;
    private Integer minorUnits;
    private String status;
}
