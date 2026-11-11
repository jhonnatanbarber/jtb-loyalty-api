package com.jtb.loyalty.domain.auth.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChangePasswordRequestTest {

    @Test
    @DisplayName("Expone la contraseña actual y la nueva")
    void exponeLaContrasenaActualYLaNueva() {
        ChangePasswordRequest request = new ChangePasswordRequest("actual", "nueva12345");

        assertThat(request.currentPassword()).isEqualTo("actual");
        assertThat(request.newPassword()).isEqualTo("nueva12345");
    }
}
