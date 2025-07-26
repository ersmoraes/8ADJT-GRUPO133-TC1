package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;

import java.util.UUID;

public class FindRestaurantByIdUseCase {

    IRestaurantGateway gateway;

    private FindRestaurantByIdUseCase(IRestaurantGateway gateway) {
        this.gateway = gateway;
    }

    public static FindRestaurantByIdUseCase create(IRestaurantGateway restaurantGateway) {
        return new FindRestaurantByIdUseCase(restaurantGateway);
    }

    public Restaurant execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do restaurante não pode ser nulo");
        }
        Restaurant restaurantById = this.gateway.findById(id);
        if (restaurantById == null) {
            throw new IllegalArgumentException("Restaurante não encontrado com o id informado");
        }
        return restaurantById;
    }
}
