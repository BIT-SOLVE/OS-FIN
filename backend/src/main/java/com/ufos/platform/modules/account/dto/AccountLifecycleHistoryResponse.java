package com.ufos.platform.modules.account.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccountLifecycleHistoryResponse {
    private UUID id;
    private UUID accountId;
    private String action;
    private String previousStatus;
    private String newStatus;
    private String reason;
    private String actedBy;
    private LocalDateTime actedAt;
}
