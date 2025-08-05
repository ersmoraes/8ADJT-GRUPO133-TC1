package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.RestaurantPresenter;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant.*;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.RestaurantGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.UserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class RestaurantController {

    IRestaurantDataSource dataSource;
    IRestaurantGateway gateway;
    IUserGateway userGateway;

    private RestaurantController(IRestaurantDataSource dataSource, IUserDataSource userDataSource) {
        this.dataSource = dataSource;
        this.gateway = RestaurantGateway.create(dataSource);
        this.userGateway = UserGateway.create(userDataSource);
    }

    public static RestaurantController create(IRestaurantDataSource dataSource, IUserDataSource userGateway) {
        return new RestaurantController(dataSource, userGateway);
    }

    public Page<RestaurantDTO> findAll(Pageable pageable) throws IllegalArgumentException {
        var useCase = FindAllRestaurantUseCase.create(gateway);
        return useCase.execute(pageable).map(RestaurantPresenter::toDTO);
    }

    public RestaurantDTO findById(UUID id) throws IllegalArgumentException {
        var useCase = FindRestaurantByIdUseCase.create(gateway);
        var restaurant = useCase.execute(id);
        return RestaurantPresenter.toDTO(restaurant);
    }

    public RestaurantDTO findByName(String name) throws IllegalArgumentException {
        var useCase = FindRestaurantByNameUseCase.create(gateway);
        try {
            var restaurant = useCase.execute(name);
            return RestaurantPresenter.toDTO(restaurant);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao buscar restaurante por name: " + e.getMessage(), e);
        }
    }

    public RestaurantDTO createRestaurant(NewRestaurantDTO newRestaurantDTO) throws IllegalArgumentException {
        var useCase = CreateRestaurantUseCase.create(gateway, userGateway);
        try {
            var restaurant = useCase.execute(newRestaurantDTO);
            return RestaurantPresenter.toDTO(restaurant);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao criar restaurante: " + e.getMessage(), e);
        }
    }

    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO) throws IllegalArgumentException {
        var useCase = UpdateRestaurantUseCase.create(gateway, userGateway);
        try {
            var restaurant = useCase.execute(restaurantDTO);
            return RestaurantPresenter.toDTO(restaurant);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao atualizar restaurante: " + e.getMessage(), e);
        }
    }

    public void deleteRestaurant(UUID id) throws IllegalArgumentException {
        var useCase = DeleteRestaurantUseCase.create(gateway);
        try {
            useCase.execute(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao deletar restaurante: " + e.getMessage(), e);
        }
    }
}
