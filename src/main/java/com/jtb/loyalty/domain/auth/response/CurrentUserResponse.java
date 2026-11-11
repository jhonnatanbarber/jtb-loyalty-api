package com.jtb.loyalty.domain.auth.response;

import com.jtb.loyalty.domain.user.model.Role;

import java.util.UUID;

public record CurrentUserResponse(UUID id, String username, String displayName, Role role) {
}
