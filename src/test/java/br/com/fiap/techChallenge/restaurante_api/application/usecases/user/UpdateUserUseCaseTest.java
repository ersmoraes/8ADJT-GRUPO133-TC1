package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseTest {

    private IUserGateway userGateway;
    private UpdateUserUseCase updateUserUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        updateUserUseCase = UpdateUserUseCase.create(userGateway);
    }

    @Test
    void updatesUserSuccessfully() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "newName", "newEmail@example.com", "newLogin", "newPassword", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));
        User oldUser = userMock(id, "oldName", "oldEmail@example.com", "oldLogin", "oldPassword", UserType.OWNER,
                Address.create("Street", "City", "State", "ZipCode"));

        when(userGateway.findById(id)).thenReturn(oldUser);
        when(userGateway.findByEmail("newEmail@example.com")).thenReturn(Optional.empty());
        when(userGateway.updateUser(any(User.class))).thenReturn(oldUser);

        User updatedUser = updateUserUseCase.execute(userDTO);

        assertEquals("newName", updatedUser.getName());
        assertEquals("newEmail@example.com", updatedUser.getEmail());
        assertEquals(UserType.CLIENT.name(), updatedUser.getUserType().name());
        assertEquals("Street", updatedUser.getAddress().getStreet());
        assertEquals("City", updatedUser.getAddress().getCity());
        assertEquals("State", updatedUser.getAddress().getState());
        assertEquals("ZipCode", updatedUser.getAddress().getZipCode());
    }

    @Test
    void throwsExceptionWhenUserDTOIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(null));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserIdIsNull() {
        UserDTO userDTO = userDTOMock(null, "name", "email@example.com", "newLogin", "newPassword", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(userDTO));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "name", "email@example.com", "newLogin", "newPassword", UserType.OWNER,
                new AddressDTO("Street", "City", "State", "ZipCode"));

        when(userGateway.findById(id)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(userDTO));
        assertEquals("Usuário não existe com o id: " + id, exception.getMessage());
    }

    @Test
    void throwsExceptionWhenEmailAlreadyExists() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "name", "newEmail@example.com", "login", "password", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));
        User oldUser = userMock(id, "oldName", "oldEmail@example.com", "login", "password", UserType.CLIENT,
                Address.create("Street", "City", "State", "ZipCode"));

        when(userGateway.findById(id)).thenReturn(oldUser);
        when(userGateway.findByEmail("newEmail@example.com")).thenReturn(Optional.of(oldUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(userDTO));
        assertEquals("Usuário já cadastrado com o email inserido: newEmail@example.com", exception.getMessage());
    }

    private static UserDTO userDTOMock(UUID id, String name, String email, String login, String password, UserType userType,
                                       AddressDTO addressDTO) {
        return new UserDTO(
                id,
                name,
                email,
                login,
                password,
                userType,
                addressDTO,
                null,
                null);
    }

    private static User userMock(UUID id, String name, String email, String login, String password, UserType userType,
                                 Address address) {
        return User.create(id, name, email, login, password, userType, null, null, address);
    }

}