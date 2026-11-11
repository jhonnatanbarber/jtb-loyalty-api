package com.jtb.loyalty.infrastructure.security;

import com.jtb.loyalty.application.auth.TokenIssuer;
import com.jtb.loyalty.domain.user.model.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

/**
 * Emite JWT firmados con RSA (RS256) usando SmallRye JWT Build.
 * <p>
 * Claims:
 * <ul>
 *   <li>{@code sub}: id del usuario (lo usa CurrentUserProvider)</li>
 *   <li>{@code upn}: username</li>
 *   <li>{@code groups}: rol, que habilita {@code @RolesAllowed}</li>
 *   <li>{@code name}: nombre visible, para el menú lateral</li>
 * </ul>
 */
@ApplicationScoped
public class JwtTokenIssuer implements TokenIssuer {

    private final String issuer;
    private final Duration duration;

    public JwtTokenIssuer(
            @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer,
            @ConfigProperty(name = "app.security.jwt.duration", defaultValue = "PT5M") Duration duration) {
        this.issuer = issuer;
        this.duration = duration;
    }

    @Override
    public String issue(User user) {
        return Jwt.issuer(issuer)
                .subject(user.getId().toString())
                .upn(user.getUsername())
                .groups(Set.of(user.getRole().name()))
                .claim("name", user.getDisplayName())
                .claim("jti", UUID.randomUUID().toString())
                .expiresIn(duration)
                .sign();
    }

    @Override
    public long expiresInSeconds() {
        return duration.toSeconds();
    }
}