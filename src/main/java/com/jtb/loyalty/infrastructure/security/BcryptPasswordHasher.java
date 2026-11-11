package com.jtb.loyalty.infrastructure.security;

import com.jtb.loyalty.domain.auth.service.PasswordHasher;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Implementación de {@link PasswordHasher} con BCrypt.
 * El costo (cuántas veces se repite el cálculo) se configura en application.yaml.
 */
@ApplicationScoped
@Slf4j
public class BcryptPasswordHasher implements PasswordHasher {

    private static final int MIN_COST = 10;
    private static final int MAX_COST = 14;
    private final int cost;

    public BcryptPasswordHasher(
            @ConfigProperty(name = "app.security.bcrypt-cost", defaultValue = "10") int cost) {
        if (cost < MIN_COST || cost > MAX_COST) {
            throw new IllegalStateException(
                    "app.security.bcrypt-cost debe estar entre " + MIN_COST + " y " + MAX_COST + ", valor: " + cost);
        }
        this.cost = cost;
    }

    @Override
    public String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("La contraseña a cifrar no puede estar vacía");
        }
        return BcryptUtil.bcryptHash(rawPassword, cost);
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null || hashedPassword.isBlank()) {
            return false;
        }
        try {
            return BcryptUtil.matches(rawPassword, hashedPassword);
        } catch (RuntimeException ex) {
            log.warn("No se pudo verificar la contraseña: el hash guardado tiene un formato inválido");
            return false;
        }
    }
}