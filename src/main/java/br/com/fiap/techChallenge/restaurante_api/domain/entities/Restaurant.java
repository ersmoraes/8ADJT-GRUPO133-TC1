package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Restaurant {
    private UUID id;
    private String name;
    private Address address;
    private String kitchenType;
    private String openingHours;
    private User owner;

    public static Restaurant create(UUID id, String name, Address address, String kitchenType,
                                    String openingHours, User owner) {
        if (name == null || name.isEmpty() || address == null || kitchenType == null || kitchenType.isEmpty()
                || openingHours == null || openingHours.isEmpty() || owner == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos: nome, endereço, tipo de cozinha, " +
                    "horário de funcionamento ou proprietário");
        }

        return Restaurant.builder()
                .id(id)
                .name(name)
                .address(address)
                .kitchenType(kitchenType)
                .openingHours(openingHours)
                .owner(owner)
                .build();
    }

    public void setOpeningHours(String openingHours) {
        validOpeningHours(openingHours);
        this.openingHours = openingHours;
    }

    private static void validOpeningHours(String openingHours) {
        if (openingHours == null || openingHours.isEmpty() ) {
            throw new IllegalArgumentException("Horário de funcionamento não pode ser nulo.");
        }
        try {
            LocalTime.parse(openingHours);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Horário de funcionamento deve estar no formato HH:mm");
        }
    }

    public void setOwner(User owner) {
        validOwner(owner);
        this.owner = owner;
    }

    private static void validOwner(User owner) {
        if (owner == null || owner.getUserType().name().equalsIgnoreCase(UserType.CLIENTE.name())) {
            throw new IllegalArgumentException("Proprietário não pode ser nulo e precisa ser válido");
        }
    }
}
