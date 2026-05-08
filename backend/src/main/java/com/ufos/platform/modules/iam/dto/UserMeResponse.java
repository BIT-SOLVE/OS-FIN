package com.ufos.platform.modules.iam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserMeResponse {
    private String subject;
    private String username;
    private String email;
    private String displayName;
    private Set<String> roles;
    private Set<String> authorities;
    private boolean authenticated;
}
