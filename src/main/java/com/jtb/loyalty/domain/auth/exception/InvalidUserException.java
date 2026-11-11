package com.jtb.loyalty.domain.auth.exception;

import zcee.jbt.error.exception.BusinessException;
import zcee.jbt.error.shared.ErrorCode;

/**
 * Se lanza cuando los datos de un usuario no cumplen las reglas del dominio
 * (usuario vacío o con formato inválido, nombre demasiado largo, contraseña vacía).
 * Tipo, código y mensaje base: application.yaml → app.errors.INVALID_USER.
 */
public class InvalidUserException extends BusinessException {

    public InvalidUserException(String message) {
        super(ErrorCode.INVALID_USER, message);
    }
}
