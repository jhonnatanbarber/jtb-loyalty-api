package com.jtb.loyalty.application.auth.dto;

import com.jtb.loyalty.domain.user.model.Role;

import java.util.UUID;

/**
 * Datos del usuario autenticado que devuelve GetCurrentUserQuery.
 */
public record CurrentUserView(UUID id, String username, String displayName, Role role) {
}