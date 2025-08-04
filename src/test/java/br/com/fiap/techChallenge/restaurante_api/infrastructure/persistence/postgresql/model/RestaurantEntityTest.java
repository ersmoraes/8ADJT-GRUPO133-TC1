package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestaurantEntityTest {

    @Test
    void shouldCreateRestaurantEntityFromRestaurantSuccessfully() {
        UUID id = UUID.randomUUID();
        Address address = Address.create("Rua X", "Cidade Y", "Estado Z", "12345-678");
        User owner = User.create(UUID.randomUUID(), "Owner", "owner@email.com", "login", "senha", UserType.OWNER, null, null, address);
        Restaurant restaurant = Restaurant.create(id, "Restaurante", address, "Italiana", "09:00", owner);

        RestaurantEntity entity = RestaurantEntity.toRestaurantEntity(restaurant);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals("Restaurante", entity.getName());
        assertEquals("Italiana", entity.getKitchenType());
        assertEquals("09:00", entity.getOpeningHours());
        assertNotNull(entity.getAddressEntity());
        assertNotNull(entity.getOwner());
        assertEquals("Rua X", entity.getAddressEntity().getStreet());
        assertEquals("Owner", entity.getOwner().getName());
    }

    @Test
    void shouldCreateRestaurantEntityFromRestaurantDTOSuccessfully() {
        UUID id = UUID.randomUUID();
        AddressDTO addressDTO = new AddressDTO("Rua X", "Cidade Y", "Estado Z", "12345-678");
        UserDTO ownerDTO = new UserDTO(UUID.randomUUID(), "Owner", "owner@email.com", "login", "senha", UserType.OWNER, addressDTO, null, null);
        RestaurantDTO restaurantDTO = new RestaurantDTO(id, "Restaurante", addressDTO, "Italiana", "09:00", ownerDTO);

        RestaurantEntity entity = RestaurantEntity.toRestaurantEntityEntityFromDTO(restaurantDTO);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals("Restaurante", entity.getName());
        assertEquals("Italiana", entity.getKitchenType());
        assertEquals("09:00", entity.getOpeningHours());
        assertNotNull(entity.getAddressEntity());
        assertNotNull(entity.getOwner());
        assertEquals("Rua X", entity.getAddressEntity().getStreet());
        assertEquals("Owner", entity.getOwner().getName());
    }

    @Test
    void shouldConvertListOfRestaurantsToListOfRestaurantEntities() {
        Address address = Address.create("Rua 1", "Cidade 1", "Estado 1", "11111-111");
        User owner = User.create(UUID.randomUUID(), "Owner1", "owner1@email.com", "login1", "senha1", UserType.OWNER, null, null, address);
        Restaurant restaurant1 = Restaurant.create(UUID.randomUUID(), "Restaurante1", address, "Japonesa", "10:00", owner);
        Restaurant restaurant2 = Restaurant.create(UUID.randomUUID(), "Restaurante2", address, "Brasileira", "11:00", owner);

        List<RestaurantEntity> entities = RestaurantEntity.toRestaurantEntity(List.of(restaurant1, restaurant2));

        assertEquals(2, entities.size());
        assertEquals("Restaurante1", entities.get(0).getName());
        assertEquals("Restaurante2", entities.get(1).getName());
    }

    @Test
    void shouldConvertListOfRestaurantDTOsToListOfRestaurantEntities() {
        AddressDTO addressDTO = new AddressDTO("Rua 1", "Cidade 1", "Estado 1", "11111-111");
        UserDTO ownerDTO = new UserDTO(UUID.randomUUID(), "Owner1", "owner1@email.com", "login1", "senha1", UserType.OWNER, addressDTO, null, null);
        RestaurantDTO dto1 = new RestaurantDTO(UUID.randomUUID(), "Restaurante1", addressDTO, "Japonesa", "10:00", ownerDTO);
        RestaurantDTO dto2 = new RestaurantDTO(UUID.randomUUID(), "Restaurante2", addressDTO, "Brasileira", "11:00", ownerDTO);

        List<RestaurantEntity> entities = RestaurantEntity.toRestaurantEntityEntityFromDTO(List.of(dto1, dto2));

        assertEquals(2, entities.size());
        assertEquals("Restaurante1", entities.get(0).getName());
        assertEquals("Restaurante2", entities.get(1).getName());
    }
}