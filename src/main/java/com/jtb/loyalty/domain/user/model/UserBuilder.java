package com.jtb.loyalty.domain.user.model;

import com.jtb.loyalty.domain.auth.exception.InvalidUserException;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Construye instancias válidas de {@link User}.
 * <p>
 * Todas las reglas de formato del usuario viven aquí: {@code build()} las aplica al crear
 * y {@code User} las reutiliza al modificar sus datos.
 */
public final class UserBuilder {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9._-]{3,50}$");
    private static final int DISPLAY_NAME_MAX = 100;

    private UUID id;
    private String username;
    private String displayName;
    private String passwordHash;
    private Role role;
    private boolean active = true;
    private Instant createdAt;
    private Instant updatedAt;

    UserBuilder() {
    }

    public UserBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public UserBuilder passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserBuilder role(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public UserBuilder createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /** Atajo para un usuario nuevo: genera el id, lo deja activo y usa {@code now} en ambas fechas. */
    public UserBuilder asNew(Instant now) {
        this.id = UUID.randomUUID();
        this.active = true;
        this.createdAt = now;
        this.updatedAt = now;
        return this;
    }

    public User build() {
        Instant created = Objects.requireNonNull(createdAt, "createdAt");
        return new User(
                Objects.requireNonNull(id, "id"),
                normalizeUsername(username),
                validateDisplayName(displayName),
                validatePasswordHash(passwordHash),
                Objects.requireNonNull(role, "role"),
                active,
                created,
                updatedAt != null ? updatedAt : created);
    }

    static String normalizeUsername(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new InvalidUserException("El usuario es obligatorio");
        }
        String value = raw.strip().toLowerCase(Locale.ROOT);
        if (!USERNAME_PATTERN.matcher(value).matches()) {
            throw new InvalidUserException(
                    "El usuario debe tener entre 3 y 50 caracteres: letras, números, punto, guion o guion bajo");
        }
        return value;
    }

    static String validateDisplayName(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new InvalidUserException("El nombre es obligatorio");
        }
        String value = raw.strip();
        if (value.length() > DISPLAY_NAME_MAX) {
            throw new InvalidUserException("El nombre no puede superar " + DISPLAY_NAME_MAX + " caracteres");
        }
        return value;
    }

    static String validatePasswordHash(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new InvalidUserException("La contraseña es obligatoria");
        }
        return hash;
    }
}