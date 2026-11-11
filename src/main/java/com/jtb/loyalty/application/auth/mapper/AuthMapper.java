package com.jtb.loyalty.application.auth.mapper;

import com.jtb.loyalty.application.auth.dto.ChangePasswordCommand;
import com.jtb.loyalty.application.auth.dto.CurrentUserView;
import com.jtb.loyalty.application.auth.dto.LoginCommand;
import com.jtb.loyalty.application.auth.dto.TokenView;
import com.jtb.loyalty.domain.auth.request.ChangePasswordRequest;
import com.jtb.loyalty.domain.auth.request.LoginRequest;
import com.jtb.loyalty.domain.auth.response.CurrentUserResponse;
import com.jtb.loyalty.domain.auth.response.TokenResponse;

import java.util.UUID;

/**
 * Convierte entre los DTOs HTTP del módulo auth y los commands/views de la capa application.
 * Sin estado y sin dependencias: utilitario estático.
 */
public final class AuthMapper {

    private AuthMapper() {
    }

    public static LoginCommand toCommand(LoginRequest request) {
        // Solo se normaliza el usuario; la contraseña se usa exactamente como llegó.
        return new LoginCommand(request.username().strip(), request.password());
    }

    public static ChangePasswordCommand toCommand(ChangePasswordRequest request, UUID userId) {
        return new ChangePasswordCommand(userId, request.currentPassword(), request.newPassword());
    }

    public static TokenResponse toResponse(TokenView view) {
        return new TokenResponse(view.accessToken(), view.tokenType(), view.expiresIn());
    }

    public static CurrentUserResponse toResponse(CurrentUserView view) {
        return new CurrentUserResponse(view.id(), view.username(), view.displayName(), view.role());
    }
}