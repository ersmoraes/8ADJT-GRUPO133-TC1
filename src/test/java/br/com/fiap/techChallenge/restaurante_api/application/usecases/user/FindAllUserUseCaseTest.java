package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindAllUserUseCaseTest {

    private IUserGateway userGateway;
    private FindAllUserUseCase findAllUserUseCase;

    @BeforeEach
    void setup() {
        userGateway = mock(IUserGateway.class);
        findAllUserUseCase = FindAllUserUseCase.create(userGateway);
    }

    @Test
    void returnsPageOfUsersWhenPageableIsValid() {
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);

        when(userGateway.findAll(pageable)).thenReturn(userPage);

        Page<User> result = findAllUserUseCase.execute(pageable);

        assertEquals(userPage, result);
    }

    @Test
    void throwsExceptionWhenPageableIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> findAllUserUseCase.execute(null));
        assertEquals("Pageable não pode ser nulo", exception.getMessage());
    }
}