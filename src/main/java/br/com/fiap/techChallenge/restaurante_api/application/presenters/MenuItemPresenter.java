package br.com.fiap.techChallenge.restaurante_api.application.presenters;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;

public class MenuItemPresenter {

    public static MenuItemResponseDTO toDTO(MenuItemEntity menuItem) {;
        return new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isOnlyLocal(),
                menuItem.getUrlFoto()
        );
    }

    public static MenuItem toDomain(MenuItemDTO dto) {
        return MenuItem.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .onlyLocal(dto.onlyLocal())
                .urlFoto(dto.urlFoto())
                .build();
    }


}
