package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindUserByIdUseCaseTest {

    private IUserGateway userGateway;
    private FindUserByIdUseCase findUserByIdUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        findUserByIdUseCase = FindUserByIdUseCase.create(userGateway);
    }

    @Test
    void returnsUserWhenIdExists() {
        UUID id = UUID.randomUUID();
        User user = mock(User.class);

        when(userGateway.findById(id)).thenReturn(user);

        User result = findUserByIdUseCase.execute(id);

        assertEquals(user, result);
    }

    @Test
    void throwsExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByIdUseCase.execute(null));
        assertEquals("ID do usuário não pode ser nulo", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUserNotFoundById() {
        UUID id = UUID.randomUUID();

        when(userGateway.findById(id)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findUserByIdUseCase.execute(id));
        assertEquals("Usuário não encontrado com o id informado", exception.getMessage());
    }
}