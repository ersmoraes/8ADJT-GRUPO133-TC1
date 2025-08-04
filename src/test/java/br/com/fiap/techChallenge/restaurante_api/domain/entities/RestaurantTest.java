package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    Address address = new Address("Street", "City", "State", "12345-678");

    @Test
    void shouldThrowExceptionWhenOpeningHoursIsNull() {
        UUID id = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Restaurant.create(id, "Restaurant Name", address, "Italian", null, null);
        });

        assertEquals("Horário de funcionamento não pode ser nulo.", exception.getMessage());
    }

    @Test
    void shouldConvertRestaurantEntityToRestaurantSuccessfully() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(id);
        restaurantEntity.setName("Restaurant Name");
        restaurantEntity.setKitchenType("Italian");
        restaurantEntity.setOpeningHours("09:00");
        restaurantEntity.setOwner(new UserEntity());

        User owner = new User(id, "Owner Name", "owner@example.com", "ownerLogin",
                "ownerPassword", UserType.OWNER, null, null, address);

        Restaurant restaurant = Restaurant.create(id, "Restaurant Name", address, "Italian",
                "09:00", owner);

        assertNotNull(restaurant);
        assertEquals("Restaurant Name", restaurant.getName());
        assertEquals("Italian", restaurant.getKitchenType());
        assertEquals("09:00", restaurant.getOpeningHours());
        assertEquals(owner, restaurant.getOwner());
    }

    @Test
    void shouldThrowExceptionWhenOpeningHoursIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Restaurant.create(UUID.randomUUID(), "Restaurant Name", address, "Italian",
                    "08:01-12:00", null);
        });

        assertEquals("Horário de funcionamento deve estar no formato HH:mm", exception.getMessage());
    }

    @Test
    void shouldCreateRestaurantSuccessfullyWhenAllFieldsAreValid() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(id);
        restaurantEntity.setName("Restaurant Name");
        restaurantEntity.setKitchenType("Italian");
        restaurantEntity.setOpeningHours("09:00");
        UserEntity owner = new UserEntity();
        owner.setId(UUID.randomUUID());
        owner.setUserType(UserTypeEnum.OWNER);
        restaurantEntity.setOwner(owner);
        Restaurant restaurant = Restaurant.toRestaurant(restaurantEntity);

        assertNotNull(restaurant);
        assertEquals("Restaurant Name", restaurant.getName());
        assertEquals("Italian", restaurant.getKitchenType());
        assertEquals("09:00", restaurant.getOpeningHours());
    }

    @Test
    void shouldConvertRestaurantDTOToRestaurantSuccessfully() {
        UUID id = UUID.randomUUID();
        AddressDTO addressDTO = new AddressDTO("Street", "City", "State", "12345-678");
        UserDTO ownerDTO = new UserDTO(id, "Owner Name", "owner@example.com", "ownerLogin",
                "ownerPassword", UserType.OWNER, addressDTO, null, null);
        RestaurantDTO restaurantDTO = new RestaurantDTO(id, "Restaurant Name", addressDTO, "Italian",
                "09:00", ownerDTO);

        Restaurant restaurant = Restaurant.toRestaurantFromDTO(restaurantDTO);

        assertNotNull(restaurant);
        assertEquals("Restaurant Name", restaurant.getName());
        assertEquals("Italian", restaurant.getKitchenType());
        assertEquals("09:00", restaurant.getOpeningHours());
        assertEquals(ownerDTO.id(), restaurant.getOwner().getId());
    }
}