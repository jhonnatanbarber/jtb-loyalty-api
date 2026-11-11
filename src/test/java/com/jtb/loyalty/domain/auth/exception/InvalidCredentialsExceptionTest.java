package com.jtb.loyalty.domain.auth.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import zcee.jbt.error.shared.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InvalidCredentialsExceptionTest {

    @Test
    @DisplayName("Usa el ErrorCode INVALID_CREDENTIALS")
    void usaElErrorCodeInvalidCredentials() {
        InvalidCredentialsException exception = new InvalidCredentialsException();

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_CREDENTIALS);
        assertThat(exception.getDetail()).isNull();
    }
}
