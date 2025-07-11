package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

import java.util.UUID;

public record RestaurantDTO(
        UUID id,
        String name,
        String endereco,
        String kitchenType,
        String openingHours,
        UserDTO owner
) {
}
