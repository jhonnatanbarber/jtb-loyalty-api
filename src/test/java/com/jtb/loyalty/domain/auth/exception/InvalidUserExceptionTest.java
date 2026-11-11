package com.jtb.loyalty.domain.auth.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zcee.jbt.error.shared.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InvalidUserExceptionTest {

    @Test
    @DisplayName("Usa el ErrorCode INVALID_USER y expone el mensaje como detail")
    void usaElErrorCodeInvalidUser_yExponeElMensajeComoDetail() {
        InvalidUserException exception = new InvalidUserException("El usuario es obligatorio");

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_USER);
        assertThat(exception.getMessage()).isEqualTo("El usuario es obligatorio");
        assertThat(exception.getDetail()).isEqualTo("El usuario es obligatorio");
    }
}
