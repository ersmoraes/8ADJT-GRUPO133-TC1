package br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class RestaurantGateway implements IRestaurantGateway {
    private final IRestaurantDataSource dataSource;

    private RestaurantGateway(IRestaurantDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RestaurantGateway create(IRestaurantDataSource dataSource) {
        return new RestaurantGateway(dataSource);
    }

    @Override
    public Page<Restaurant> findAll(Pageable pageable) {
        return this.dataSource.findAll(pageable).map(Restaurant::toRestaurantFromDTO);
    }

    @Override
    public Restaurant findById(UUID id) {
        Optional<RestaurantDTO> restaurantDTOOptional = this.dataSource.findById(id);
        if (restaurantDTOOptional.isEmpty()) {
            throw new IllegalArgumentException("Restaurante não encontrado com o ID: " + id);
        }
        RestaurantDTO restaurantDTO = restaurantDTOOptional.get();
        return dtoToRestaurant(restaurantDTO);
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        Optional<RestaurantDTO> restaurantDTOOptional = this.dataSource.findByName(name);
        if (restaurantDTOOptional.isEmpty()) {
            return Optional.empty();
        }
        RestaurantDTO restaurantDTO = restaurantDTOOptional.get();
        return Optional.of(dtoToRestaurant(restaurantDTO));
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO(
                restaurant.getName(),
                AddressDTO.toAddressDTO(restaurant.getAddress()),
                restaurant.getKitchenType(),
                restaurant.getOpeningHours(),
                UserDTO.toUserDTO(restaurant.getOwner())
        );
        RestaurantDTO restaurantDTO = dataSource.createRestaurant(newRestaurantDTO);
        return dtoToRestaurant(restaurantDTO);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        RestaurantDTO RestaurantDTO = new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                AddressDTO.toAddressDTO(restaurant.getAddress()),
                restaurant.getKitchenType(),
                restaurant.getOpeningHours(),
                UserDTO.toUserDTO(restaurant.getOwner())
        );
        RestaurantDTO restaurantDTO = dataSource.updateRestaurant(RestaurantDTO);
        return dtoToRestaurant(restaurantDTO);
    }

    @Override
    public void deleteRestaurant(UUID id) {
        this.dataSource.deleteRestaurant(id);
    }

    private static Restaurant dtoToRestaurant(RestaurantDTO restaurantDTO) {
        if (restaurantDTO.addressDTO() == null) {
            return Restaurant.create(
                    restaurantDTO.id(),
                    restaurantDTO.name(),
                    null,
                    restaurantDTO.kitchenType(),
                    restaurantDTO.openingHours(),
                    null);
        }
        return Restaurant.create(
                restaurantDTO.id(),
                restaurantDTO.name(),
                restaurantDTO.addressDTO().parser(),
                restaurantDTO.kitchenType(),
                restaurantDTO.openingHours(),
                restaurantDTO.owner().parser());
    }
}
