package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.MenuItemRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements IMenuItemDataSource {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuItemDTO create(MenuItemDTO dto) {
        MenuItemEntity menuItem = new MenuItemEntity(null, dto.name(), dto.description(), dto.price(), dto.onlyLocal(), dto.urlFoto());
        MenuItemEntity save = menuItemRepository.save(menuItem);
        return new MenuItemDTO(save.getId(), save.getName(), save.getDescription(), save.getPrice(), save.isOnlyLocal(), save.getUrlFoto());
    }

    @Override
    public Page<MenuItemDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public MenuItemDTO findById(UUID id) {
        return null;
    }

    @Override
    public MenuItemDTO findByName(String name) {
        MenuItemDTO dto = menuItemRepository.findByName(name);
        MenuItemEntity menuItem = new MenuItemEntity(dto.id(), dto.name(), dto.description(), dto.price(), dto.onlyLocal(), dto.urlFoto());
        return new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getDescription(),
                menuItem.getPrice(), menuItem.isOnlyLocal(), menuItem.getUrlFoto());
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
//        userRepository.deleteById(id);
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
