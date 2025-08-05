package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.MenuItemRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceImplTest {

    @Mock
    MenuItemRepository menuItemRepository;
    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    MenuItemServiceImpl service;

    @Test
    void shouldCreateMenuItem() {
        MenuItemDTO dto = new MenuItemDTO(null, "Pizza", "Deliciosa", BigDecimal.TEN, false, "url");
        MenuItemEntity entity = new MenuItemEntity(UUID.randomUUID(), "Pizza", "Deliciosa", BigDecimal.TEN, false, "url");

        when(menuItemRepository.save(any(MenuItemEntity.class))).thenReturn(entity);

        MenuItemDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals("Pizza", result.name());
        assertEquals("Deliciosa", result.description());
        assertEquals(BigDecimal.TEN, result.price());
        assertEquals("url", result.urlFoto());
    }

    @Test
    void shouldReturnNullWhenFindAll() {
        assertNull(service.findAll(null));
    }

    @Test
    void shouldReturnNullWhenFindById() {
        assertNull(service.findById(UUID.randomUUID()));
    }

    @Test
    void shouldFindByName() {
        UUID id = UUID.randomUUID();
        MenuItemEntity menuItemEntity = new MenuItemEntity(id, "Pizza", "Deliciosa", BigDecimal.TEN, false, "url");
        when(menuItemRepository.findByName("Pizza")).thenReturn(menuItemEntity);

        MenuItemDTO result = service.findByName("Pizza");

        assertNotNull(result);
        assertEquals("Pizza", result.name());
        assertEquals("Deliciosa", result.description());
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(menuItemRepository.existsByName("Pizza")).thenReturn(true);
        assertTrue(service.existsByName("Pizza"));
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(menuItemRepository.existsByName("Sushi")).thenReturn(false);
        assertFalse(service.existsByName("Sushi"));
    }
}