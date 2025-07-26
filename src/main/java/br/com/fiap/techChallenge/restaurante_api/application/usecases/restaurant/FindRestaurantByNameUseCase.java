package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;

public class FindRestaurantByNameUseCase {

    IRestaurantGateway restaurantGateway;

    private FindRestaurantByNameUseCase(IRestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public static FindRestaurantByNameUseCase create(IRestaurantGateway restaurantGateway) {
        return new FindRestaurantByNameUseCase(restaurantGateway);
    }

    public Restaurant execute(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio");
        }
        return this.restaurantGateway.findByName(name.trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o name: " + name));
    }
}
