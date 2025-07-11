package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Restaurant {
    private UUID id;
    private String name;
    private Address endereco;
    private String kitchenType;
    private String openingHours;
    private User owner;

    public static Restaurant create(UUID id, String name, Address endereco, String kitchenType,
                                    String openingHours, User owner) {
        if (name == null || name.isEmpty() || endereco == null || kitchenType == null || kitchenType.isEmpty()
                || openingHours == null || openingHours.isEmpty() || owner == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos: nome, endereço, tipo de cozinha, " +
                    "horário de funcionamento ou proprietário");
        }

        return Restaurant.builder()
                .id(id)
                .name(name)
                .endereco(endereco)
                .kitchenType(kitchenType)
                .openingHours(openingHours)
                .owner(owner)
                .build();
    }
}
