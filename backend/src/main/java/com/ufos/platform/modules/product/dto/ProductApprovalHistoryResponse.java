package com.ufos.platform.modules.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ProductApprovalHistoryResponse {
    private UUID id;
    private UUID productId;
    private String action;
    private String previousStatus;
    private String newStatus;
    private String comment;
    private String actedBy;
    private LocalDateTime actedAt;
}
