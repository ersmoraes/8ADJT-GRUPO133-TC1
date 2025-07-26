package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;

import java.util.UUID;

public class DeleteRestaurantUseCase {

    IRestaurantGateway restaurantGateway;

    private DeleteRestaurantUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public static DeleteRestaurantUseCase create(IRestaurantGateway restaurantGateway) {
        return new DeleteRestaurantUseCase(restaurantGateway);
    }

    public void execute(UUID id) {
        this.restaurantGateway.deleteRestaurant(id);
    }
}
