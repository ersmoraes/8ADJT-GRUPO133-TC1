package br.com.fiap.techChallenge.restaurante_api.application.presenters;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;

public class RestaurantPresenter {

    public static RestaurantDTO toDTO(Restaurant restaurant) {
        if (restaurant.getAddress() == null) {
            return new RestaurantDTO(
                    restaurant.getId(),
                    restaurant.getName(),
                    null,
                    restaurant.getKitchenType(),
                    restaurant.getOpeningHours(),
                    null);
        }
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                AddressDTO.toAddressDTO(restaurant.getAddress()),
                restaurant.getKitchenType(),
                restaurant.getOpeningHours(),
                UserDTO.toUserDTO(restaurant.getOwner()));
    }
}
