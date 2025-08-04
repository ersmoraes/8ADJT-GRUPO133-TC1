package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FindRestaurantByNameUseCaseTest {

    @Test
    void throwsExceptionWhenRestaurantNameIsNull() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        FindRestaurantByNameUseCase useCase = FindRestaurantByNameUseCase.create(restaurantGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
        assertEquals("Nome do restaurante não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenRestaurantNameIsEmpty() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        FindRestaurantByNameUseCase useCase = FindRestaurantByNameUseCase.create(restaurantGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute("   "));
        assertEquals("Nome do restaurante não pode ser nulo ou vazio", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenRestaurantNotFound() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        String name = "RestauranteInexistente";

        when(restaurantGateway.findByName(name)).thenReturn(Optional.empty());

        FindRestaurantByNameUseCase useCase = FindRestaurantByNameUseCase.create(restaurantGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(name));
        assertEquals("Restaurante não encontrado com o name: " + name, exception.getMessage());
    }

    @Test
    void returnsRestaurantWhenValidNameIsProvided() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        String name = "RestauranteValido";
        Restaurant expectedRestaurant = mock(Restaurant.class);

        when(restaurantGateway.findByName(name)).thenReturn(Optional.of(expectedRestaurant));

        FindRestaurantByNameUseCase useCase = FindRestaurantByNameUseCase.create(restaurantGateway);

        Restaurant result = useCase.execute(name);

        assertEquals(expectedRestaurant, result);
        verify(restaurantGateway, times(1)).findByName(name);
    }
}