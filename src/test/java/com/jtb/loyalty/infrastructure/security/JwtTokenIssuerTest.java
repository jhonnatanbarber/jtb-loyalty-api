package com.jtb.loyalty.infrastructure.security;

import com.jtb.loyalty.domain.user.model.Role;
import com.jtb.loyalty.domain.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * La firma del JWT usa SmallRye JWT Build, que resuelve la clave privada vía MicroProfile
 * Config ("smallrye.jwt.sign.key.location"). Como esa propiedad vive en application.yml y aquí
 * no se arranca Quarkus, se fija como system property apuntando al mismo recurso del classpath.
 */
@ExtendWith(MockitoExtension.class)
class JwtTokenIssuerTest {

    private static final String SIGN_KEY_PROPERTY = "smallrye.jwt.sign.key.location";
    private static final Duration DURATION = Duration.parse("PT12H");

    private JwtTokenIssuer tokenIssuer;

    @BeforeEach
    void setUp() {
        System.setProperty(SIGN_KEY_PROPERTY, "config/privateKey.pem");
        tokenIssuer = new JwtTokenIssuer("jtb-loyalty-api", DURATION);
    }

    @AfterEach
    void tearDown() {
        System.clearProperty(SIGN_KEY_PROPERTY);
    }

    @Test
    @DisplayName("expiresInSeconds devuelve la duración configurada")
    void expiresInSeconds_devuelveLaDuracionConfigurada() {
        assertThat(tokenIssuer.expiresInSeconds()).isEqualTo(DURATION.toSeconds());
    }

    @Test
    @DisplayName("issue firma un JWT con los claims del usuario")
    void issue_firmaUnJwtConLosClaimsDelUsuario() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("juan.perez")
                .displayName("Juan Perez")
                .passwordHash("hash")
                .role(Role.ADMIN)
                .active(true)
                .createdAt(Instant.now())
                .build();

        String token = tokenIssuer.issue(user);

        assertThat(token.split("\\.")).hasSize(3);
        String payload = new String(
                Base64.getUrlDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
        assertThat(payload)
                .contains(user.getId().toString())
                .contains("juan.perez")
                .contains("Juan Perez")
                .contains("ADMIN");
    }
}
