package com.jtb.loyalty.application.auth.dto;

/**
 * Datos de entrada del caso de uso de inicio de sesión.
 *
 * @param username usuario ya normalizado (sin espacios al inicio ni al final)
 * @param password contraseña en texto plano, tal como la escribió el usuario
 */
public record LoginCommand(String username, String password) {
}