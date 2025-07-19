package br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IRestaurantDataSource {
    Page<RestaurantDTO> findAll(Pageable pageable);

    Optional<RestaurantDTO> findById(UUID id);

    Optional<RestaurantDTO> findByName(String name);

    RestaurantDTO createRestaurant(NewRestaurantDTO dto);

    RestaurantDTO updateRestaurant(RestaurantDTO dto);

    void deleteRestaurant(UUID id);
}
