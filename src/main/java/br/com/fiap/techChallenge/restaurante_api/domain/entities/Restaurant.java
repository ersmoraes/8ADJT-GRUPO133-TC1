package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
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
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos: name, endereço, tipo de cozinha, " +
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

    public static Restaurant toRestaurant(RestaurantEntity restaurantEntity) {
        return Restaurant.builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .owner(User.toUser(restaurantEntity.getOwner()))
                .address(Address.toAddress(restaurantEntity.getAddressEntity()))
                .kitchenType(restaurantEntity.getKitchenType())
                .openingHours(restaurantEntity.getOpeningHours())
                .build();
    }

    public static List<Restaurant> toRestaurant(List<RestaurantEntity> restaurantEntities) {
        return restaurantEntities.stream()
                .map(Restaurant::toRestaurant)
                .toList();
    }

    public static Restaurant toRestaurantFromDTO(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .id(restaurantDTO.id())
                .name(restaurantDTO.name())
                .owner(User.toUserFromDTO(restaurantDTO.owner()))
                .address(Address.toAddressFromDTO(restaurantDTO.addressDTO()))
                .kitchenType(restaurantDTO.kitchenType())
                .openingHours(restaurantDTO.openingHours())
                .build();
    }

    public static List<Restaurant> toRestaurantFromDTO(List<RestaurantDTO> restaurantDTOS) {
        return restaurantDTOS.stream()
                .map(Restaurant::toRestaurantFromDTO)
                .toList();
    }
}
