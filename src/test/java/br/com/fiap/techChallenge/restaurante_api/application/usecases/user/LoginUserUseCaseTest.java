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

class LoginUserUseCaseTest {

    private IUserGateway userGateway;
    private LoginUserUseCase loginUserUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        loginUserUseCase = LoginUserUseCase.create(userGateway);
    }

    @Test
    void returnsSuccessMessageWhenLoginIsValid() {
        String login = "validLogin";
        String password = "validPassword";
        User user = mock(User.class);

        when(userGateway.findByLogin(login)).thenReturn(Optional.of(user));
        when(userGateway.findByLoginAndPassword(login, password)).thenReturn(Optional.of(user));

        String result = loginUserUseCase.execute(login, password);

        assertEquals("Login Efetuado com sucesso!", result);
    }

    @Test
    void returnsErrorMessageWhenPasswordIsInvalid() {
        String login = "validLogin";
        String password = "invalidPassword";
        User user = mock(User.class);

        when(userGateway.findByLogin(login)).thenReturn(Optional.of(user));
        when(userGateway.findByLoginAndPassword(login, password)).thenReturn(Optional.empty());

        String result = loginUserUseCase.execute(login, password);

        assertEquals("Usuario ou senha invalidos!", result);
    }

    @Test
    void throwsExceptionWhenLoginIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.execute(null, "password"));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenPasswordIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.execute("login", null));
        assertEquals("Dados do usuário não podem ser nulos", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserNotFoundByLogin() {
        String login = "nonExistentLogin";
        String password = "password";

        when(userGateway.findByLogin(login)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.execute(login, password));
        assertEquals("Usuário não encontrado com o login: " + login, exception.getMessage());
    }
}