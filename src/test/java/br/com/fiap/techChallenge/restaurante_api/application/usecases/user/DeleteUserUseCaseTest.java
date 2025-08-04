package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

    private IUserGateway userGateway;
    private DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        deleteUserUseCase = DeleteUserUseCase.create(userGateway);
    }

    @Test
    void deletesUserWhenIdIsValid() {
        UUID id = UUID.randomUUID();

        doNothing().when(userGateway).deleteUser(id);

        assertDoesNotThrow(() -> deleteUserUseCase.execute(id));
        verify(userGateway, times(1)).deleteUser(id);
    }

    @Test
    void throwsExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deleteUserUseCase.execute(null));
        assertEquals("ID do usuário não pode ser nulo", exception.getMessage());
    }
}