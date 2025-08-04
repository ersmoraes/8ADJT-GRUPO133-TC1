package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseTest {

    AddressDTO addressDTO = new AddressDTO("Street", "City", "State", "12345-678");

    @Test
    void throwsExceptionWhenNewRestaurantDTOIsNull() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        CreateRestaurantUseCase createRestaurantUseCase = CreateRestaurantUseCase.create(restaurantGateway, userGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createRestaurantUseCase.execute(null));
        assertEquals("Dados do restaurante não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenOwnerDoesNotExist() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO("Restaurant Name", addressDTO, "Delivery",
                "09:00-18:00", userDTOMock(UUID.randomUUID(), addressDTO));

        when(userGateway.findById(newRestaurantDTO.owner().id())).thenReturn(null);

        CreateRestaurantUseCase createRestaurantUseCase = CreateRestaurantUseCase.create(restaurantGateway, userGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createRestaurantUseCase.execute(newRestaurantDTO));
        assertEquals("Usuário dono não existe com o id: " + newRestaurantDTO.owner().login(), exception.getMessage());
    }

    @Test
    void createRestaurantWhenDataIsValid() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO("Restaurant Name", addressDTO,
                "Delivery", "09:00", userDTOMock(UUID.randomUUID(), addressDTO));
        User owner = mock(User.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(userGateway.findById(newRestaurantDTO.owner().id())).thenReturn(owner);
        when(restaurantGateway.createRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        CreateRestaurantUseCase createRestaurantUseCase = CreateRestaurantUseCase.create(restaurantGateway, userGateway);

        Restaurant result = createRestaurantUseCase.execute(newRestaurantDTO);

        assertEquals(restaurant, result);
        verify(restaurantGateway, times(1)).createRestaurant(any(Restaurant.class));
    }

    private static UserDTO userDTOMock(UUID id, AddressDTO addressDTO) {
        return new UserDTO(
                id,
                "name",
                "email@example.com",
                "login",
                "password",
                UserType.OWNER,
                addressDTO,
                null,
                null);
    }
}