package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FindAllRestaurantUseCaseTest {

    @Test
    void throwsExceptionWhenPageableIsNull() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        FindAllRestaurantUseCase useCase = FindAllRestaurantUseCase.create(restaurantGateway);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
        assertEquals("Pageable não pode ser nulo", exception.getMessage());
    }

    @Test
    void returnsPaginatedRestaurantsWhenPageableIsValid() {
        IRestaurantGateway restaurantGateway = mock(IRestaurantGateway.class);
        Pageable pageable = mock(Pageable.class);
        Page<Restaurant> expectedPage = new PageImpl<>(List.of(mock(Restaurant.class)));

        when(restaurantGateway.findAll(pageable)).thenReturn(expectedPage);

        FindAllRestaurantUseCase useCase = FindAllRestaurantUseCase.create(restaurantGateway);

        Page<Restaurant> result = useCase.execute(pageable);

        assertEquals(expectedPage, result);
        verify(restaurantGateway, times(1)).findAll(pageable);
    }
}