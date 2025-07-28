package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestaurantDTO(
        UUID id,
        String name,
        AddressDTO addressDTO,
        String kitchenType,
        String openingHours,
        UserDTO owner
) {
    public Restaurant parser() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setAddress(addressDTO.parser());
        restaurant.setKitchenType(kitchenType);
        restaurant.setOpeningHours(openingHours);
        restaurant.setOwner(owner.parser());

        return restaurant;
    }

    public static RestaurantDTO toRestaurantDTOFromEntity(RestaurantEntity restaurantEntity) {
        return RestaurantDTO.builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .addressDTO(AddressDTO.toAddressDTOFromAddressEntity(restaurantEntity.getAddressEntity()))
                .kitchenType(restaurantEntity.getKitchenType())
                .openingHours(restaurantEntity.getOpeningHours())
                .owner(UserDTO.toUserDTOFromEntity(restaurantEntity.getOwner()))
                .build();
    }

    public static List<RestaurantDTO> toRestaurantDTOFromEntity(List<RestaurantEntity> restaurantEntities) {
        return restaurantEntities.stream()
                .map(RestaurantDTO::toRestaurantDTOFromEntity)
                .toList();
    }

    public static RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .addressDTO(AddressDTO.toAddressDTO(restaurant.getAddress()))
                .kitchenType(restaurant.getKitchenType())
                .openingHours(restaurant.getOpeningHours())
                .owner(UserDTO.toUserDTO(restaurant.getOwner()))
                .build();
    }

    public static List<RestaurantDTO> toRestaurantDTO(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantDTO::toRestaurantDTO)
                .toList();
    }
}
