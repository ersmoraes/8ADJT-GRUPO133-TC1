package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEntity {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private AddressEntity addressEntity;

    @Column(name = "tipo_cozinha", nullable = false)
    private String kitchenType;

    @Column(name = "horario_funcionamento", nullable = false)
    private String openingHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    public static RestaurantEntity toRestaurantEntity(Restaurant restaurant) {
        return RestaurantEntity.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .addressEntity(AddressEntity.toAddressEntity(restaurant.getAddress()))
                .kitchenType(restaurant.getKitchenType())
                .openingHours(restaurant.getOpeningHours())
                .owner(UserEntity.toUserEntity(restaurant.getOwner()))
                .build();
    }

    public static List<RestaurantEntity> toRestaurantEntity(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantEntity::toRestaurantEntity)
                .toList();
    }

    public static RestaurantEntity toRestaurantEntityEntityFromDTO(RestaurantDTO restaurantDTO) {
        return RestaurantEntity.builder()
                .id(restaurantDTO.id())
                .name(restaurantDTO.name())
                .addressEntity(AddressEntity.toAddressEntityFromDTO(restaurantDTO.addressDTO()))
                .kitchenType(restaurantDTO.kitchenType())
                .openingHours(restaurantDTO.openingHours())
                .owner(UserEntity.toUserEntityFromDTO(restaurantDTO.owner()))
                .build();
    }

    public static List<RestaurantEntity> toRestaurantEntityEntityFromDTO(List<RestaurantDTO> restaurantsDTO) {
        return restaurantsDTO.stream()
                .map(RestaurantEntity::toRestaurantEntityEntityFromDTO)
                .toList();
    }
}
