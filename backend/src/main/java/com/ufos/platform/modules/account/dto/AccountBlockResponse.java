package com.ufos.platform.modules.account.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccountBlockResponse {
    private UUID id;
    private UUID accountId;
    private String blockReference;
    private BigDecimal blockAmount;
    private String blockReason;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime releasedAt;
    private String createdBy;
    private String releasedBy;
}
