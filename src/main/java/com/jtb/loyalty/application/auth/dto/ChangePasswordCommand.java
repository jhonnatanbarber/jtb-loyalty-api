package com.jtb.loyalty.application.auth.dto;

import java.util.UUID;

/**
 * Datos de entrada del caso de uso de cambio de contraseña.
 *
 * @param userId          usuario autenticado; sale del JWT, nunca del body
 * @param currentPassword contraseña actual en texto plano, para confirmar identidad
 * @param newPassword     nueva contraseña en texto plano; el caso de uso la cifra con BCrypt
 */
public record ChangePasswordCommand(UUID userId, String currentPassword, String newPassword) {
}