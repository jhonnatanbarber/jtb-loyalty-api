package com.jtb.loyalty.application.auth.dto;

/**
 * Resultado del inicio de sesión que devuelve LoginUseCase.
 *
 * @param accessToken JWT firmado
 * @param tokenType   tipo de token; siempre "Bearer"
 * @param expiresIn   segundos de validez del token
 */
public record TokenView(String accessToken, String tokenType, long expiresIn) {

    @Override
    public String toString() {
        return "TokenView[accessToken=****, tokenType=" + tokenType + ", expiresIn=" + expiresIn + "]";
    }
}