package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindRestaurantByIdUseCaseTest {

    @Test
    void throwsExceptionWhenRestaurantIdIsNull() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        FindRestaurantByIdUseCase useCase = FindRestaurantByIdUseCase.create(restaurantGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
        assertEquals("ID do restaurante não pode ser nulo", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenRestaurantNotFound() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        UUID id = UUID.randomUUID();

        when(restaurantGateway.findById(id)).thenReturn(null);

        FindRestaurantByIdUseCase useCase = FindRestaurantByIdUseCase.create(restaurantGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(id));
        assertEquals("Restaurante não encontrado com o id informado", exception.getMessage());
    }

    @Test
    void returnsRestaurantWhenValidIdIsProvided() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        UUID id = UUID.randomUUID();
        Restaurant expectedRestaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(id)).thenReturn(expectedRestaurant);

        FindRestaurantByIdUseCase useCase = FindRestaurantByIdUseCase.create(restaurantGateway);

        Restaurant result = useCase.execute(id);

        assertEquals(expectedRestaurant, result);
        verify(restaurantGateway, times(1)).findById(id);
    }
}