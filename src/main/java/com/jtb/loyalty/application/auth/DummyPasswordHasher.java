package com.jtb.loyalty.application.auth;

import com.jtb.loyalty.domain.auth.service.PasswordHasher;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

/**
 * Hash señuelo para igualar el tiempo de respuesta cuando el usuario no existe,
 * de modo que no se pueda distinguir por timing si un usuario está registrado o no.
 */
@ApplicationScoped
public class DummyPasswordHasher {

    @Inject
    PasswordHasher passwordHasher;

    private String dummyHash;

    @PostConstruct
    void init() {
        dummyHash = passwordHasher.hash(UUID.randomUUID().toString());
    }

    public void match(String rawPassword) {
        passwordHasher.matches(rawPassword, dummyHash);
    }
}
