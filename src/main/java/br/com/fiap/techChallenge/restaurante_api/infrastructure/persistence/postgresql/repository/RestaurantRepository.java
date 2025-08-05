package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantRepository implements IRestaurantDataSource {

    private final IRestaurantRepository restaurantRepository;
    private final IUserRepository userRepository;

    @Override
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        return restaurantRepository.findAll(pageable)
                .map(RestaurantDTO::toRestaurantDTOFromEntity);
    }

    @Override
    public Optional<RestaurantDTO> findById(UUID id) {
        return restaurantRepository.findById(id)
                .map(RestaurantDTO::toRestaurantDTOFromEntity);
    }

    @Override
    public Optional<RestaurantDTO> findByName(String name) {
        return restaurantRepository.findByName(name)
                .map(RestaurantDTO::toRestaurantDTOFromEntity);
    }

    @Override
    public RestaurantDTO createRestaurant(NewRestaurantDTO restaurantDTO) {
        UserEntity owner = userRepository.findById(restaurantDTO.owner().id())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário (dono) não encontrado"));
        RestaurantEntity restaurante = RestaurantEntity.builder()
                .name(restaurantDTO.name())
                .addressEntity(new AddressEntity(restaurantDTO.address().street(),
                        restaurantDTO.address().city(),
                        restaurantDTO.address().state(),
                        restaurantDTO.address().zipCode()))
                .kitchenType(restaurantDTO.kitchenType())
                .openingHours(restaurantDTO.openingHours())
                .owner(owner)
                .build();

        return RestaurantDTO.toRestaurantDTOFromEntity(restaurantRepository.save(restaurante));
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO dto) {
        RestaurantEntity restauranteUpdated = restaurantRepository
                .save(RestaurantEntity.toRestaurantEntityEntityFromDTO(dto));

        return RestaurantDTO.toRestaurantDTOFromEntity(restauranteUpdated);
    }

    @Override
    public void deleteRestaurant(UUID id) {
        RestaurantEntity restaurante = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
        restaurantRepository.delete(restaurante);
    }
}
