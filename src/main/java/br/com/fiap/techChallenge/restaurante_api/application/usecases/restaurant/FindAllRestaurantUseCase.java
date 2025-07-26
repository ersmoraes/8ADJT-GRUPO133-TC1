package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FindAllRestaurantUseCase {

    IRestaurantGateway restaurantGateway;

    private FindAllRestaurantUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public static FindAllRestaurantUseCase create(IRestaurantGateway restaurantGateway) {
        return new FindAllRestaurantUseCase(restaurantGateway);
    }

    public Page<Restaurant> execute(Pageable pageable) {
        return this.restaurantGateway.findAll(pageable);
    }
}
