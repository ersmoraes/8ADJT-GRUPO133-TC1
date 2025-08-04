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

class FindUserByEmailUseCaseTest {

    private IUserGateway userGateway;
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        findUserByEmailUseCase = FindUserByEmailUseCase.create(userGateway);
    }

    @Test
    void returnsUserWhenEmailExists() {
        String email = "validEmail@example.com";
        User user = mock(User.class);

        when(userGateway.findByEmail(email)).thenReturn(Optional.of(user));

        User result = findUserByEmailUseCase.execute(email);

        assertEquals(user, result);
    }

    @Test
    void throwsExceptionWhenEmailIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByEmailUseCase.execute(null));
        assertEquals("Email do usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenEmailIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByEmailUseCase.execute("   "));
        assertEquals("Email do usuário não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserNotFoundByEmail() {
        String email = "nonExistentEmail@example.com";

        when(userGateway.findByEmail(email)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByEmailUseCase.execute(email));
        assertEquals("Usuário não encontrado com o email: " + email, exception.getMessage());
    }
}