package com.jtb.loyalty.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class BcryptPasswordHasherTest {

    private BcryptPasswordHasher hasher;

    @BeforeEach
    void setUp() {
        hasher = new BcryptPasswordHasher(10);
    }

    @Test
    @DisplayName("El constructor rechaza un costo menor al mínimo")
    void constructor_rechazaUnCostoMenorAlMinimo() {
        assertThatThrownBy(() -> new BcryptPasswordHasher(9))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("El constructor rechaza un costo mayor al máximo")
    void constructor_rechazaUnCostoMayorAlMaximo() {
        assertThatThrownBy(() -> new BcryptPasswordHasher(15))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("hash genera un hash distinto al texto plano")
    void hash_generaUnHashDistintoAlTextoPlano() {
        String hash = hasher.hash("secret123");

        assertThat(hash).isNotEqualTo("secret123");
        assertThat(hash).isNotBlank();
    }

    @Test
    @DisplayName("hash rechaza contraseña vacía o nula")
    void hash_rechazaContrasenaVaciaONula() {
        assertThatThrownBy(() -> hasher.hash(""))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> hasher.hash(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("matches es verdadero cuando la contraseña corresponde al hash")
    void matches_esVerdadero_cuandoLaContrasenaCorrespondeAlHash() {
        String hash = hasher.hash("secret123");

        assertThat(hasher.matches("secret123", hash)).isTrue();
    }

    @Test
    @DisplayName("matches es falso cuando la contraseña no corresponde")
    void matches_esFalso_cuandoLaContrasenaNoCorresponde() {
        String hash = hasher.hash("secret123");

        assertThat(hasher.matches("otra-clave", hash)).isFalse();
    }

    @Test
    @DisplayName("matches es falso cuando el hash es nulo o en blanco")
    void matches_esFalso_cuandoElHashEsNuloOBlanco() {
        assertThat(hasher.matches("secret123", null)).isFalse();
        assertThat(hasher.matches("secret123", " ")).isFalse();
    }

    @Test
    @DisplayName("matches es falso cuando la contraseña es nula")
    void matches_esFalso_cuandoLaContrasenaEsNula() {
        assertThat(hasher.matches(null, hasher.hash("secret123"))).isFalse();
    }

    @Test
    @DisplayName("matches es falso cuando el hash tiene formato inválido")
    void matches_esFalso_cuandoElHashTieneFormatoInvalido() {
        assertThat(hasher.matches("secret123", "formato-invalido")).isFalse();
    }
}
