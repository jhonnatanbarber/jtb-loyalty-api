package com.jtb.loyalty.domain.auth.exception;

import zcee.jbt.error.exception.BusinessException;
import zcee.jbt.error.shared.ErrorCode;

/**
 * Credenciales inválidas en el inicio de sesión.
 * <p>
 * Se usa siempre el mismo error, sin importar si el usuario no existe, la contraseña
 * es incorrecta o el usuario está desactivado, para no revelar qué usuarios existen.
 * Tipo, código y mensaje: application.yaml → app.errors.INVALID_CREDENTIALS.
 */
public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}