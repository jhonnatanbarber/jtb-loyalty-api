package zcee.jbt.error.shared;

/**
 * Categorías de error del dominio. El dominio no conoce HTTP:
 * la capa web traduce cada tipo a su código de estado.
 */
public enum ErrorType {
    VALIDATION,     // 400
    UNAUTHORIZED,   // 401
    NOT_FOUND,      // 404
    CONFLICT,       // 409
    BUSINESS_RULE,  // 422
    INTERNAL        // 500
}