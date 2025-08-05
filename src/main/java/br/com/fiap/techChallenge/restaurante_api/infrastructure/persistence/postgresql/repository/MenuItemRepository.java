package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return null;
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

//
//    @Override
//    public List<MenuItemResponseDTO> listarTodos() {
//        return menuItemRepository.findAll().stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public MenuItemResponseDTO buscarPorId(UUID id) {
//        MenuItemEntity item = menuItemRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
//        return toDTO(item);
//    }
//
//    @Override
//    public MenuItemResponseDTO atualizar(UUID id, MenuItemRequestDTO dto) {
//        MenuItemEntity item = menuItemRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
//
//        RestaurantEntity restaurant = restaurantRepository.findById(dto.restauranteId())
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
//
//        item.setName(dto.name());
//        item.setDescription(dto.description());
//        item.setPrice(dto.price());
//        item.setOnlyLocal(dto.onlyLocal());
//        item.setUrlFoto(dto.imgUrl());
//        item.setRestaurant(restaurant);
//
//        menuItemRepository.save(item);
//
//        return toDTO(item);
//    }
//
//    @Override
//    public void deletar(UUID id) {
//        MenuItemEntity item = menuItemRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
//        menuItemRepository.delete(item);
//    }
//
//    private MenuItemResponseDTO toDTO(MenuItemEntity item) {
//        return new MenuItemResponseDTO(
//                item.getId(),
//                item.getName(),
//                item.getDescription(),
//                item.getPrice(),
//                item.isOnlyLocal(),
//                item.getUrlFoto(),
//                item.getRestaurant().getName()
//        );
//    }
}
