package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantRequestDTO {
    private String name;
    private AddressRequestDTO address;
    private String kitchenType;
    private String openingHours;
    private UUID ownerId;
}
