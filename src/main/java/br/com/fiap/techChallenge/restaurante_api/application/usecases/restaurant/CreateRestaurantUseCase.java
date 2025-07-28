package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class CreateRestaurantUseCase {

    IRestaurantGateway restaurantGateway;
    IUserGateway userGateway;

    private CreateRestaurantUseCase(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public static CreateRestaurantUseCase create(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        return new CreateRestaurantUseCase(restaurantGateway, userGateway);
    }

    public Restaurant execute(NewRestaurantDTO newRestaurantDTO) throws IllegalArgumentException {
        if (newRestaurantDTO == null || newRestaurantDTO.name() == null || newRestaurantDTO.owner() == null
                || newRestaurantDTO.owner().id() == null
                || newRestaurantDTO.kitchenType() == null || newRestaurantDTO.address() == null) {
            throw new IllegalArgumentException("Dados do restaurante não podem ser nulos");
        }

        User userById = this.userGateway.findById(newRestaurantDTO.owner().id());
        if (userById == null) {
            throw new IllegalArgumentException("Usuário dono não existe com o id: " +
                    newRestaurantDTO.owner().login());
        }

        final Restaurant restaurant = Restaurant.create(null, newRestaurantDTO.name(), newRestaurantDTO.address().parser(),
                newRestaurantDTO.kitchenType(), newRestaurantDTO.openingHours(), newRestaurantDTO.owner().parser());

        return this.restaurantGateway.createRestaurant(restaurant);
    }
}
