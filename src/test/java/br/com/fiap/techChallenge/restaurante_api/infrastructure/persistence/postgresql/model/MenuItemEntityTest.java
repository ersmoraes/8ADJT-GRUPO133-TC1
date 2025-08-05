package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemEntityTest {

    @Test
    void shouldCreateMenuItemEntitySuccessfullyWithAllFields() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .id(UUID.randomUUID())
                .name("Restaurante Exemplo")
                .build();

        MenuItemEntity menuItem = MenuItemEntity.builder()
                .id(id)
                .name("Prato do Dia")
                .description("Descrição do prato")
                .price(new BigDecimal("29.90"))
                .onlyLocal(false)
                .urlFoto("http://imagem.com/foto.jpg")
                .build();

        assertNotNull(menuItem);
        assertEquals(id, menuItem.getId());
        assertEquals("Prato do Dia", menuItem.getName());
        assertEquals("Descrição do prato", menuItem.getDescription());
        assertEquals(new BigDecimal("29.90"), menuItem.getPrice());
        assertFalse(menuItem.isOnlyLocal());
        assertEquals("http://imagem.com/foto.jpg", menuItem.getUrlFoto());
    }

    @Test
    void shouldAllowNullDescriptionAndUrlFoto() {
        MenuItemEntity menuItem = MenuItemEntity.builder()
                .name("Prato")
                .price(new BigDecimal("10.00"))
                .onlyLocal(true)
                .description(null)
                .urlFoto(null)
                .build();

        assertNull(menuItem.getDescription());
        assertNull(menuItem.getUrlFoto());
    }

    @Test
    void shouldAssociateWithRestaurantEntity() {
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .id(UUID.randomUUID())
                .name("Restaurante Teste")
                .build();

        MenuItemEntity menuItem = MenuItemEntity.builder()
                .name("Prato")
                .price(new BigDecimal("15.00"))
                .build();

    }
}