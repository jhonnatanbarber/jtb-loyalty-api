package com.jtb.loyalty.application.auth;

import com.jtb.loyalty.domain.user.model.User;

/**
 * Puerto para emitir el token de acceso de un usuario autenticado.
 * La capa application no sabe si es JWT u otro formato: eso lo decide infrastructure.
 */
public interface TokenIssuer {

    /**
     * Genera un token firmado para el usuario.
     * Solo debe llamarse después de validar sus credenciales.
     */
    String issue(User user);

    /** Segundos de validez del token, para informarlo al frontend. */
    long expiresInSeconds();
}