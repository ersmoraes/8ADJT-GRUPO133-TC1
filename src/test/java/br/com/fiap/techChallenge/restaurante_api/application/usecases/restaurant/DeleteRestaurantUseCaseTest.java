package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteRestaurantUseCaseTest {

    private IRestaurantGateway restaurantGateway;
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantGateway = Mockito.mock(IRestaurantGateway.class);
        deleteRestaurantUseCase = DeleteRestaurantUseCase.create(restaurantGateway);
    }

    @Test
    @DisplayName("Should delete restaurant when valid ID is provided")
    void shouldDeleteRestaurantWithValidId() {
        UUID validId = UUID.randomUUID();

        deleteRestaurantUseCase.execute(validId);

        verify(restaurantGateway, times(1)).deleteRestaurant(validId);
    }

    @Test
    @DisplayName("Should throw exception when null ID is provided")
    void shouldThrowExceptionWithNullId() {
        UUID nullId = null;

        assertThrows(IllegalArgumentException.class, () -> deleteRestaurantUseCase.execute(nullId));
        verify(restaurantGateway, never()).deleteRestaurant(any());
    }

    @Test
    @DisplayName("Should throw exception when restaurantGateway fails")
    void shouldThrowExceptionWhenGatewayFails() {
        UUID validId = UUID.randomUUID();
        doThrow(new RuntimeException("Gateway error")).when(restaurantGateway).deleteRestaurant(validId);

        assertThrows(RuntimeException.class, () -> deleteRestaurantUseCase.execute(validId));
        verify(restaurantGateway, times(1)).deleteRestaurant(validId);
    }
}