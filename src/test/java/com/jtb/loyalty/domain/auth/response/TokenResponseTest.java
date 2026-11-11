package com.jtb.loyalty.domain.auth.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TokenResponseTest {

    @Test
    @DisplayName("Expone accessToken, tipo y expiración")
    void exponeAccessTokenTipoYExpiracion() {
        TokenResponse response = new TokenResponse("jwt-abc", "Bearer", 3600L);

        assertThat(response.accessToken()).isEqualTo("jwt-abc");
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.expiresIn()).isEqualTo(3600L);
    }
}
