package com.jtb.loyalty.shared.roles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RolesTest {

    @Test
    @DisplayName("Define las constantes ADMIN y BARBER")
    void defineLasConstantesAdminYBarber() {
        assertThat(Roles.ADMIN).isEqualTo("ADMIN");
        assertThat(Roles.BARBER).isEqualTo("BARBER");
    }
}
