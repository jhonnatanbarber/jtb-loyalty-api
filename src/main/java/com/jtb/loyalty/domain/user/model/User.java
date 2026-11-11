package com.jtb.loyalty.domain.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Usuario del panel (ADMIN o BARBER). Aggregate root del módulo auth.
 * Se crea solo a través de {@link UserBuilder}.
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @EqualsAndHashCode.Include
    private final UUID id;
    private final String username;
    private String displayName;

    @ToString.Exclude // El hash nunca debe aparecer en logs
    private String passwordHash;

    private Role role;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    /** Package-private: solo {@link UserBuilder} puede llamarlo, y ya entrega los datos validados. */
    User(UUID id, String username, String displayName, String passwordHash,
         Role role, boolean active, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // ---------- Comportamiento ----------

    public void changePassword(String newPasswordHash, Instant now) {
        this.passwordHash = UserBuilder.validatePasswordHash(newPasswordHash);
        this.updatedAt = now;
    }

    public void rename(String newDisplayName, Instant now) {
        this.displayName = UserBuilder.validateDisplayName(newDisplayName);
        this.updatedAt = now;
    }

    public void changeRole(Role newRole, Instant now) {
        this.role = Objects.requireNonNull(newRole, "role");
        this.updatedAt = now;
    }

    public void deactivate(Instant now) {
        this.active = false;
        this.updatedAt = now;
    }

    public void activate(Instant now) {
        this.active = true;
        this.updatedAt = now;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}