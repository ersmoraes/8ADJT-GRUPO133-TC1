package br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemGatewayTest {

    private IMenuItemDataSource dataSource;
    private MenuItemGateway gateway;

    @BeforeEach
    void setUp() {
        dataSource = mock(IMenuItemDataSource.class);
        gateway = MenuItemGateway.create(dataSource);
    }

    @Test
    void shouldCreateMenuItem() {
        MenuItem menuItem = MenuItem.create(null, "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        MenuItemDTO dto = new MenuItemDTO(UUID.randomUUID(), "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        when(dataSource.create(any(MenuItemDTO.class))).thenReturn(dto);

        MenuItem result = gateway.createMenuItem(menuItem);

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        assertEquals("Saborosa", result.getDescription());
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        MenuItemDTO dto = new MenuItemDTO(id, "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        when(dataSource.findById(id)).thenReturn(dto);

        MenuItem result = gateway.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldFindByName() {
        MenuItemDTO dto = new MenuItemDTO(UUID.randomUUID(), "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        when(dataSource.findByName("Pizza")).thenReturn(dto);

        MenuItem result = gateway.findByName("Pizza");

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(dataSource.existsByName("Pizza")).thenReturn(true);
        assertTrue(gateway.existsByName("Pizza"));
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(dataSource.existsByName("Sushi")).thenReturn(false);
        assertFalse(gateway.existsByName("Sushi"));
    }

    @Test
    void shouldReturnPageOfMenuItemsWhenFindAll() {
        Pageable pageable = mock(Pageable.class);
        MenuItemDTO dto = new MenuItemDTO(UUID.randomUUID(), "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        Page<MenuItemDTO> page = new PageImpl<>(List.of(dto));
        when(dataSource.findAll(pageable)).thenReturn(page);

        Page<MenuItem> result = gateway.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Pizza", result.getContent().get(0).getName());
    }

    @Test
    void shouldDeleteMenuItem() {
        UUID id = UUID.randomUUID();
        doNothing().when(dataSource).deleteMenuItem(id);

        assertDoesNotThrow(() -> gateway.deleteMenuItem(id));
        verify(dataSource, times(1)).deleteMenuItem(id);
    }

    @Test
    void shouldUpdateMenuItem() {
        MenuItem menuItem = MenuItem.create(UUID.randomUUID(), "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        doNothing().when(dataSource).update(menuItem);

        assertDoesNotThrow(() -> gateway.update(menuItem));
        verify(dataSource, times(1)).update(menuItem);
    }
}