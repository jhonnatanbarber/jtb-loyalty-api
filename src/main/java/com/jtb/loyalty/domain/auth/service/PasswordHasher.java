package com.jtb.loyalty.domain.auth.service;

/**
 * Puerto para cifrar y verificar contraseñas.
 * El dominio no sabe qué algoritmo se usa: la implementación vive en infrastructure.
 */
public interface PasswordHasher {

    /**
     * Genera el hash de una contraseña en texto plano.
     * Cada llamada produce un hash distinto (incluye una sal aleatoria).
     */
    String hash(String rawPassword);

    /**
     * Verifica si una contraseña en texto plano corresponde a un hash guardado.
     * Nunca lanza excepción: ante un hash inválido devuelve {@code false}.
     */
    boolean matches(String rawPassword, String hashedPassword);
}