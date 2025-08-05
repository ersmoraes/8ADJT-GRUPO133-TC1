package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    @Test
    void shouldCreateMenuItemSuccessfullyWithAllFields() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = MenuItem.create(
                id,
                "Prato do Dia",
                "Descrição do prato",
                new BigDecimal("29.90"),
                false,
                "http://imagem.com/foto.jpg"
        );

        assertNotNull(menuItem);
        assertEquals("Prato do Dia", menuItem.getName());
        assertEquals("Descrição do prato", menuItem.getDescription());
        assertEquals(new BigDecimal("29.90"), menuItem.getPrice());
        assertFalse(menuItem.isOnlyLocal());
        assertEquals("http://imagem.com/foto.jpg", menuItem.getUrlFoto());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = MenuItem.create(
                id,
                "Prato do Dia",
                "Descrição do prato",
                new BigDecimal("10.00"),
                false,
                null
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            menuItem.setPrice(null);
        });

        assertEquals("Preço não pode ser nulo ou negativo.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = MenuItem.create(
                id,
                "Prato do Dia",
                "Descrição do prato",
                new BigDecimal("10.00"),
                false,
                null
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            menuItem.setPrice(new BigDecimal("-1.00"));
        });

        assertEquals("Preço não pode ser nulo ou negativo.", exception.getMessage());
    }
}