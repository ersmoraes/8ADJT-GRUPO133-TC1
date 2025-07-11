package br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IRestaurantDataSource {
    RestaurantDTO create(RestaurantDTO dto);

    Page<RestaurantDTO> findAll(Pageable pageable);

    RestaurantDTO findById(UUID id);

    RestaurantDTO findByName(String name);
}
