package br.com.fiap.techChallenge.restaurante_api.application.presenters;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
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
}
