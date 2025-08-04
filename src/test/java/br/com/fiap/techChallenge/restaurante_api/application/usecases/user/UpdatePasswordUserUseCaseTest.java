package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdatePasswordUserUseCaseTest {

    private IUserGateway userGateway;
    private UpdatePasswordUserUseCase updatePasswordUserUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        updatePasswordUserUseCase = UpdatePasswordUserUseCase.create(userGateway);
    }

    @Test
    void updatesPasswordSuccessfully() {
        UUID id = UUID.randomUUID();
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserDTO userDTO = userDTOMock(id, "name", "email@example.com", "login", newPassword, UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));
        User oldUser = userMock(id, "name", "email@example.com", "login", oldPassword, UserType.CLIENT,
                Address.create("Street", "City", "State", "ZipCode"));

        when(userGateway.findById(id)).thenReturn(oldUser);
        when(userGateway.updatePassword(any(User.class), eq(oldPassword))).thenReturn(oldUser);

        User updatedUser = updatePasswordUserUseCase.execute(userDTO, oldPassword);

        assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void throwsExceptionWhenUserDTOIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updatePasswordUserUseCase.execute(null, "oldPassword"));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserIdIsNull() {
        UserDTO userDTO = userDTOMock(null, "name", "email@example.com", "login", "newPassword", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updatePasswordUserUseCase.execute(userDTO, "oldPassword"));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenOldPasswordIsNull() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "name", "email@example.com", "login", "newPassword", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updatePasswordUserUseCase.execute(userDTO, null));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenNewPasswordIsNull() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "name", "email@example.com", "login", null, UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updatePasswordUserUseCase.execute(userDTO, "oldPassword"));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "name", "email@example.com", "login", "newPassword", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));

        when(userGateway.findById(id)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updatePasswordUserUseCase.execute(userDTO, "oldPassword"));
        assertEquals("Usuário não existe com o id: " + id, exception.getMessage());
    }

    @Test
    void throwsExceptionWhenOldPasswordIsInvalid() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = userDTOMock(id, "name", "email@example.com", "login", "newPassword", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "ZipCode"));
        User oldUser = userMock(id, "name", "email@example.com", "login", "oldPassword", UserType.CLIENT,
                Address.create("Street", "City", "State", "ZipCode"));

        when(userGateway.findById(id)).thenReturn(oldUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> updatePasswordUserUseCase.execute(userDTO, "wrongPassword"));
        assertEquals("Senha antiga inválida", exception.getMessage());
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