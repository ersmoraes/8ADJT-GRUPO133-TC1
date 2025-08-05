package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemRepository implements IMenuItemDataSource {
    
    private final IMenuItemRepository menuItemRepository;

    @Override
    public MenuItemDTO create(MenuItemDTO dto) {
        MenuItemEntity menuItem = new MenuItemEntity(null, dto.name(), dto.description(), dto.price(), dto.onlyLocal(), dto.urlFoto());
        MenuItemEntity save= menuItemRepository.save(menuItem);
        return new MenuItemDTO(save.getId(), save.getName(), save.getDescription(), save.getPrice(), save.isOnlyLocal(), save.getUrlFoto());
    }

    @Override
    public Page<MenuItemDTO> findAll(Pageable pageable) {
        Page<MenuItemEntity> all = menuItemRepository.findAll(pageable);
        Page<MenuItemDTO> toDto = all.map(dto -> new MenuItemDTO
                (dto.getId(),dto.getName(),dto.getDescription(), dto.getPrice(),dto.isOnlyLocal(), dto.getUrlFoto()));
        return toDto;
    }

    @Override
    public MenuItemDTO findById(UUID id) {
        MenuItemEntity menu = menuItemRepository.findById(id).orElseThrow();
        MenuItemDTO dto = new MenuItemDTO(menu.getId(), menu.getName(), menu.getDescription(), menu.getPrice(),menu.isOnlyLocal() ,menu.getUrlFoto());
        return dto;
    }

    @Override
    public MenuItemDTO findByName(String name) {
        MenuItem menuItem = menuItemRepository.findByName(name);
        MenuItemDTO dto = new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getDescription(),
                menuItem.getPrice(), menuItem.isOnlyLocal(), menuItem.getUrlFoto());
        return dto;
    }

    @Override
    public boolean existsByName(String name) {
        return menuItemRepository.existsByName(name);
    }

    @Override
    public MenuItemEntity save(MenuItemEntity menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(UUID id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public void update(MenuItem menuItem) {
        MenuItemEntity existing = menuItemRepository.findById(menuItem.getId())
                .orElseThrow(() -> new RuntimeException("MenuItem não encontrado com ID: " + menuItem.getId()));

        existing.setName(menuItem.getName());
        existing.setDescription(menuItem.getDescription());
        existing.setPrice(menuItem.getPrice());
        existing.setOnlyLocal(menuItem.isOnlyLocal());
        existing.setUrlFoto(menuItem.getUrlFoto());

        menuItemRepository.save(existing);
    }

}
