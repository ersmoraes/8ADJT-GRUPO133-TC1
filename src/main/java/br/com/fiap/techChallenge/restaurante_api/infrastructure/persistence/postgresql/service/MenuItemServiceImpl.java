package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.MenuItemRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.MenuItemRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements IMenuItemDataSource {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuItemDTO create(MenuItemDTO dto) {
        return null;
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
        return null;
    }

//    @Override
//    public MenuItemResponseDTO cadastrar(MenuItemRequestDTO dto) {
//        RestaurantEntity restaurante = restaurantRepository.findById(dto.restauranteId())
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
//
//        MenuItemEntity item = MenuItemEntity.builder()
//                .name(dto.name())
//                .description(dto.descricao())
//                .price(dto.preco())
//                .onlyLocal(dto.apenasNoLocal())
//                .urlFoto(dto.caminhoFoto())
//                .restaurant(restaurante)
//                .build();
//
//        menuItemRepository.save(item);
//
//        return toDTO(item);
//    }
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
//        item.setDescription(dto.descricao());
//        item.setPrice(dto.preco());
//        item.setOnlyLocal(dto.apenasNoLocal());
//        item.setUrlFoto(dto.caminhoFoto());
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
