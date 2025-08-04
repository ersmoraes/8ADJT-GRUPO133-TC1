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

class FindUserByLoginUseCaseTest {

    private IUserGateway userGateway;
    private FindUserByLoginUseCase findUserByLoginUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        findUserByLoginUseCase = FindUserByLoginUseCase.create(userGateway);
    }

    @Test
    void returnsUserWhenLoginExists() {
        String login = "validLogin";
        User user = mock(User.class);

        when(userGateway.findByLogin(login)).thenReturn(Optional.of(user));

        User result = findUserByLoginUseCase.execute(login);

        assertEquals(user, result);
    }

    @Test
    void throwsExceptionWhenLoginIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginUseCase.execute(null));
        assertEquals("Login do usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenLoginIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginUseCase.execute("   "));
        assertEquals("Login do usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserNotFoundByLogin() {
        String login = "nonExistentLogin";

        when(userGateway.findByLogin(login)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByLoginUseCase.execute(login));
        assertEquals("Usuário não encontrado com o login: " + login, exception.getMessage());
    }
}