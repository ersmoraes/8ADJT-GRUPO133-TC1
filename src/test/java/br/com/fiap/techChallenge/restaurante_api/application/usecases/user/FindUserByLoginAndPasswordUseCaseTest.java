package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindUserByLoginAndPasswordUseCaseTest {

    private IUserGateway userGateway;
    private FindUserByLoginAndPasswordUseCase findUserByLoginAndPasswordUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        findUserByLoginAndPasswordUseCase = FindUserByLoginAndPasswordUseCase.create(userGateway);
    }

    @Test
    void returnsUserWhenLoginAndPasswordAreValid() {
        String login = "validLogin";
        String password = "validPassword";
        User user = mock(User.class);

        when(userGateway.findByLoginAndPassword(login, password)).thenReturn(Optional.of(user));

        User result = findUserByLoginAndPasswordUseCase.execute(login, password);

        assertEquals(user, result);
    }

    @Test
    void throwsExceptionWhenLoginIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginAndPasswordUseCase.execute(null, "password"));
        assertEquals("Login e senha do usuário não podem ser nulos ou vazios!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenPasswordIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginAndPasswordUseCase.execute("login", null));
        assertEquals("Login e senha do usuário não podem ser nulos ou vazios!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenLoginIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginAndPasswordUseCase.execute("   ", "password"));
        assertEquals("Login e senha do usuário não podem ser nulos ou vazios!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenPasswordIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginAndPasswordUseCase.execute("login", "   "));
        assertEquals("Login e senha do usuário não podem ser nulos ou vazios!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserNotFoundByLoginAndPassword() {
        String login = "nonExistentLogin";
        String password = "password";

        when(userGateway.findByLoginAndPassword(login, password)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginAndPasswordUseCase.execute(login, password));
        assertEquals("Usuário não encontrado com o login: " + login, exception.getMessage());
    }
}