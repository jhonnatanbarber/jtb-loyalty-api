package zcee.jbt.error.shared;


/**
 * Claves de los errores de negocio. Tipo, código público y mensaje
 * se definen en application.yaml (app.errors.<CLAVE>).
 */
public enum ErrorCode {
    INVALID_CREDENTIALS,
    INVALID_USER,
    USER_NOT_FOUND,
    WRONG_CURRENT_PASSWORD,
    INVALID_CUSTOMER,
    CUSTOMER_NOT_FOUND,
    PHONE_ALREADY_REGISTERED,
    CARD_NOT_FOUND,
    CARD_FULL,
    CARD_NOT_ACTIVE,
    UNDO_NOT_ALLOWED,
    REWARD_NOT_FOUND,
    REWARD_NOT_AVAILABLE,
    VALIDATION_ERROR,
    MALFORMED_BODY,
    INTERNAL_ERROR
}