package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

public record NewRestaurantDTO(
        String name,
        AddressDTO address,
        String kitchenType,
        String openingHours,
        UserDTO owner
) {
}
