package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDTO implements Serializable {

    public RestaurantResponseDTO(RestaurantDTO restaurant) {
        this.id = restaurant.id();
        this.name = restaurant.name();
        this.address = restaurant.addressDTO();
        this.kitchenType = restaurant.kitchenType();
        this.openingHours = restaurant.openingHours();
        this.owner = restaurant.owner();
    }

    private UUID id;
    private String name;
    private AddressDTO address;
    private String kitchenType;
    private String openingHours;
    private UserDTO owner;
}
