package com.jtb.loyalty.application.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TokenViewTest {

    @Test
    @DisplayName("Expone los datos del token")
    void exponeLosDatosDelToken() {
        TokenView view = new TokenView("jwt-abc", "Bearer", 3600L);

        assertThat(view.accessToken()).isEqualTo("jwt-abc");
        assertThat(view.tokenType()).isEqualTo("Bearer");
        assertThat(view.expiresIn()).isEqualTo(3600L);
    }

    @Test
    @DisplayName("toString no expone el accessToken")
    void toStringNoExponeElAccessToken() {
        TokenView view = new TokenView("jwt-abc", "Bearer", 3600L);

        assertThat(view.toString())
                .doesNotContain("jwt-abc")
                .contains("****")
                .contains("Bearer")
                .contains("3600");
    }
}
