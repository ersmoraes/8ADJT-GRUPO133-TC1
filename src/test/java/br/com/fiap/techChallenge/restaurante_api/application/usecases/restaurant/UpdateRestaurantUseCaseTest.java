package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateRestaurantUseCaseTest {

    @Test
    void throwsExceptionWhenRestaurantDTOIsNull() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.create(restaurantGateway, userGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
        assertEquals("Dados do restaurante não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenRestaurantIdIsNull() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.create(restaurantGateway, userGateway);

        RestaurantDTO dto = mock(RestaurantDTO.class);
        when(dto.id()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto));
        assertEquals("Dados do restaurante não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenRestaurantDoesNotExist() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.create(restaurantGateway, userGateway);

        UUID id = UUID.randomUUID();
        RestaurantDTO dto = mock(RestaurantDTO.class);
        when(dto.id()).thenReturn(id);
        when(restaurantGateway.findById(id)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto));
        assertEquals("Restaurante não existe com o id: " + id, exception.getMessage());
    }

    @Test
    void throwsExceptionWhenOwnerIdIsDifferentAndUserNotFound() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.create(restaurantGateway, userGateway);

        UUID restaurantId = UUID.randomUUID();
        UUID oldOwnerId = UUID.randomUUID();
        UUID newOwnerId = UUID.randomUUID();

        User oldOwner = mock(User.class);
        when(oldOwner.getId()).thenReturn(oldOwnerId);

        Restaurant oldRestaurant = mock(Restaurant.class);
        when(oldRestaurant.getOwner()).thenReturn(oldOwner);

        RestaurantDTO dto = mock(RestaurantDTO.class);
        UserDTO ownerDTO = mock(UserDTO.class);
        when(dto.id()).thenReturn(restaurantId);
        when(dto.owner()).thenReturn(ownerDTO);
        when(ownerDTO.id()).thenReturn(newOwnerId);

        when(restaurantGateway.findById(restaurantId)).thenReturn(oldRestaurant);
        when(userGateway.findById(newOwnerId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto));
        assertEquals("Usuário dono não encontrado com o login: " + newOwnerId, exception.getMessage());
    }

    @Test
    void updatesRestaurantSuccessfullyWhenAllFieldsAreValid() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        IUserGateway userGateway = mock(IUserGateway.class);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.create(restaurantGateway, userGateway);

        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        Address oldAddress = Address.create("Rua A", "Cidade A", "Estado A", "00000-000");
        User oldOwner = User.create(ownerId, "Old Name", "Old Email", "oldlogin", "password",
                UserType.OWNER, null, null, oldAddress);
        Restaurant oldRestaurant = Restaurant.create(restaurantId, "Old Name", oldAddress, "Italiana",
                "10:00", oldOwner);

        AddressDTO newAddressDTO = new AddressDTO("Rua B", "Cidade B", "Estado B", "11111-111");
        UserDTO newOwnerDTO = new UserDTO(ownerId, "newName", "newEmail@example.com", "newLogin",
                "newPassword", UserType.OWNER,
                new AddressDTO("Street", "City", "State", "ZipCode"), null, null);
        RestaurantDTO dto = new RestaurantDTO(restaurantId, "New Name", newAddressDTO, "Japonesa",
                "09:00", newOwnerDTO);

        when(restaurantGateway.findById(restaurantId)).thenReturn(oldRestaurant);
        when(userGateway.findById(ownerId)).thenReturn(oldOwner);
        when(restaurantGateway.updateRestaurant(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant updated = useCase.execute(dto);

        assertEquals("New Name", updated.getName());
        assertEquals("Japonesa", updated.getKitchenType());
        assertEquals("09:00", updated.getOpeningHours());
        assertEquals("Rua B", updated.getAddress().getStreet());
        assertEquals("Cidade B", updated.getAddress().getCity());
        assertEquals("Estado B", updated.getAddress().getState());
        assertEquals("11111-111", updated.getAddress().getZipCode());
        verify(restaurantGateway, times(1)).updateRestaurant(any(Restaurant.class));
    }
}