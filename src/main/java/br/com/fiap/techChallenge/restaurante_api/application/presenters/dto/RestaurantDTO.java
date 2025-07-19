package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
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

    public static RestaurantDTO toRestaurantDTO(RestaurantEntity restaurantEntity) {
        return RestaurantDTO.builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .addressDTO(AddressDTO.toAddressDTOFromAddressEntity(restaurantEntity.getAddressEntity()))
                .kitchenType(restaurantEntity.getKitchenType())
                .openingHours(restaurantEntity.getOpeningHours())
                .owner(UserDTO.toUserDTO(restaurantEntity.getOwner()))
                .build();
    }

    public static List<RestaurantDTO> toRestaurantDTO(List<RestaurantEntity> restaurantEntities) {
        return restaurantEntities.stream()
                .map(RestaurantDTO::toRestaurantDTO)
                .toList();
    }
}
