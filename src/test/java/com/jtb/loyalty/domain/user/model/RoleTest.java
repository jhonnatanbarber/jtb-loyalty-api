package com.jtb.loyalty.domain.user.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoleTest {

    @Test
    @DisplayName("Define exactamente ADMIN y BARBER")
    void defineExactamenteAdminYBarber() {
        assertThat(Role.values()).containsExactly(Role.ADMIN, Role.BARBER);
    }

    @Test
    @DisplayName("valueOf resuelve cada constante")
    void valueOfResuelveCadaConstante() {
        assertThat(Role.valueOf("ADMIN")).isEqualTo(Role.ADMIN);
        assertThat(Role.valueOf("BARBER")).isEqualTo(Role.BARBER);
    }
}
