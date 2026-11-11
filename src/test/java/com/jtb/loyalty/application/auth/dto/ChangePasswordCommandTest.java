package com.jtb.loyalty.application.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChangePasswordCommandTest {

    @Test
    @DisplayName("Expone el userId y las contraseñas con las que se construyó")
    void exponeUserIdYContrasenas() {
        UUID userId = UUID.randomUUID();
        ChangePasswordCommand command = new ChangePasswordCommand(userId, "actual", "nueva12345");

        assertThat(command.userId()).isEqualTo(userId);
        assertThat(command.currentPassword()).isEqualTo("actual");
        assertThat(command.newPassword()).isEqualTo("nueva12345");
    }
}
