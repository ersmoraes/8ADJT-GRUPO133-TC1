package br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.MenuItemRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface IMenuItemDataSource {
    MenuItemDTO create(MenuItemDTO dto);

    Page<MenuItemDTO> findAll(Pageable pageable);

    MenuItemDTO findById(UUID id);

    MenuItemDTO findByName(String name);

    boolean existsByName(String name);

    MenuItemEntity save(MenuItemEntity menuItem);

    void deleteMenuItem(UUID id);

    public void update(MenuItem menuItem);

}
