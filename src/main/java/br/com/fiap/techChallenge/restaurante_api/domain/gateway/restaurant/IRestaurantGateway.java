package br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IRestaurantGateway {
    Page<Restaurant> findAll(Pageable pageable);

    Restaurant findById(UUID id);

    Optional<Restaurant> findByName(String name);

    Restaurant createRestaurant(Restaurant Restaurant);

    Restaurant updateRestaurant(Restaurant Restaurant);

    void deleteRestaurant(UUID id);

}
