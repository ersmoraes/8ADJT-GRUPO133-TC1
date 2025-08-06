package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemRepositoryTest {

    private IMenuItemRepository jpaRepository;
    private MenuItemRepository repository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(IMenuItemRepository.class);
        repository = new MenuItemRepository(jpaRepository);
    }

    @Test
    void shouldCreateMenuItem() {
        MenuItemDTO dto = new MenuItemDTO(null, "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        MenuItemEntity entity = new MenuItemEntity(UUID.randomUUID(), "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        when(jpaRepository.save(any(MenuItemEntity.class))).thenReturn(entity);

        MenuItemDTO result = repository.create(dto);

        assertNotNull(result);
        assertEquals("Pizza", result.name());
    }

    @Test
    void shouldReturnPageOfMenuItemsWhenFindAll() {
        Pageable pageable = mock(Pageable.class);
        MenuItemEntity entity = new MenuItemEntity(UUID.randomUUID(), "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        Page<MenuItemEntity> page = new PageImpl<>(java.util.List.of(entity));
        when(jpaRepository.findAll(pageable)).thenReturn(page);

        Page<MenuItemDTO> result = repository.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Pizza", result.getContent().get(0).name());
    }

    @Test
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity(id, "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        MenuItemDTO result = repository.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> repository.findById(id));
    }

    @Test
    void shouldFindByName() {
        MenuItem menuItem = MenuItem.create(
                UUID.randomUUID(),
                "x-burger",
                "x-burger with cheese",
                new BigDecimal("19.90"),
                true,
                "C:/");
        when(jpaRepository.findByName("Pizza")).thenReturn(menuItem);

        MenuItemDTO result = repository.findByName("Pizza");

        assertNotNull(result);
        assertEquals("x-burger", result.name());
    }

    @Test
    void shouldReturnTrueWhenExistsByName() {
        when(jpaRepository.existsByName("Pizza")).thenReturn(true);
        assertTrue(repository.existsByName("Pizza"));
    }

    @Test
    void shouldReturnFalseWhenNotExistsByName() {
        when(jpaRepository.existsByName("Sushi")).thenReturn(false);
        assertFalse(repository.existsByName("Sushi"));
    }

    @Test
    void shouldDeleteMenuItem() {
        UUID id = UUID.randomUUID();
        doNothing().when(jpaRepository).deleteById(id);

        assertDoesNotThrow(() -> repository.deleteMenuItem(id));
        verify(jpaRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldUpdateMenuItem() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = MenuItem.create(
                id,
                "x-burger",
                "x-burger with cheese",
                new BigDecimal("19.90"),
                true,
                "C:/");
        MenuItemEntity entity = new MenuItemEntity(id, "Pizza", "Saborosa", BigDecimal.TEN, false, "url");
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(jpaRepository.save(any(MenuItemEntity.class))).thenReturn(entity);

        assertDoesNotThrow(() -> repository.update(menuItem));
        verify(jpaRepository, times(1)).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenUpdateMenuItemNotFound() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = MenuItem.create(
                id,
                "x-burger",
                "x-burger with cheese",
                new BigDecimal("19.90"),
                true,
                "C:/");
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.update(menuItem));
        assertTrue(exception.getMessage().contains("MenuItem não encontrado com ID"));
    }
}