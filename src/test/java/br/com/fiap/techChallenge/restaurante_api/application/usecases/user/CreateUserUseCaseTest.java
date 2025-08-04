package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateUserUseCaseTest {

    @Test
    void throwsExceptionWhenNewUserDTOIsNull() {
        IUserGateway userGateway = mock(IUserGateway.class);
        CreateUserUseCase createUserUseCase = CreateUserUseCase.create(userGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createUserUseCase.execute(null));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenEmailAlreadyExists() {
        String email = "existingEmail@example.com";
        NewUserDTO newUserDTO = new NewUserDTO("Name", email, "login", "password", UserType.CLIENT,
                new AddressDTO("Street", "City", "State", "12345-678"));
        User existingUser = mock(User.class);
        IUserGateway userGateway = mock(IUserGateway.class);

        when(userGateway.findByEmail(email)).thenReturn(Optional.of(existingUser));

        CreateUserUseCase createUserUseCase = CreateUserUseCase.create(userGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createUserUseCase.execute(newUserDTO));
        assertEquals("Usuário já cadastrado com o email: " + email, exception.getMessage());
    }

    @Test
    void throwsExceptionWhenLoginAlreadyExists() {
        String login = "existingLogin";
        NewUserDTO newUserDTO = new NewUserDTO("Name", "email@example.com", login, "password", UserType.CLIENT, new AddressDTO("Street", "City", "State", "12345-678"));
        User existingUser = mock(User.class);
        IUserGateway userGateway = mock(IUserGateway.class);

        when(userGateway.findByLogin(login)).thenReturn(Optional.of(existingUser));

        CreateUserUseCase createUserUseCase = CreateUserUseCase.create(userGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createUserUseCase.execute(newUserDTO));
        assertEquals("Usuário já cadastrado com o login: " + login, exception.getMessage());
    }
}