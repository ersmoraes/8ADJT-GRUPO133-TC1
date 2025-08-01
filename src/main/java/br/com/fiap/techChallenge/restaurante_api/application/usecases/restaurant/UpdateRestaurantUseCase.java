package br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class UpdateRestaurantUseCase {

    IRestaurantGateway restaurantGateway;
    IUserGateway userGateway;

    private UpdateRestaurantUseCase(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public static UpdateRestaurantUseCase create(IRestaurantGateway restaurantGateway, IUserGateway userGateway) {
        return new UpdateRestaurantUseCase(restaurantGateway, userGateway);
    }

    public Restaurant execute(RestaurantDTO restaurantDTO) throws IllegalArgumentException {
        if (restaurantDTO == null || restaurantDTO.id() == null) {
            throw new IllegalArgumentException("Dados do restaurante não podem ser nulos");
        }

        Restaurant oldRestaurant = this.restaurantGateway.findById(restaurantDTO.id());
        if (oldRestaurant == null) {
            throw new IllegalArgumentException("Restaurante não existe com o id: " + restaurantDTO.id());
        }
        if (restaurantDTO.owner() != null && restaurantDTO.owner().id() != null &&
                !oldRestaurant.getOwner().getId().equals(restaurantDTO.owner().id())) {
            User userById = this.userGateway.findById(restaurantDTO.owner().id());
            if (userById == null) {
                throw new IllegalArgumentException("Usuário dono não encontrado com o login: "
                        + restaurantDTO.owner().id());
            }
        }

        if (restaurantDTO.name() != null && !restaurantDTO.name().equalsIgnoreCase(oldRestaurant.getName()))
            oldRestaurant.setName(restaurantDTO.name());
        if (restaurantDTO.owner() != null && restaurantDTO.owner().login() != null &&
                !restaurantDTO.owner().login().equalsIgnoreCase(oldRestaurant.getOwner().getLogin()))
            oldRestaurant.setOwner(User.toUserFromDTO(restaurantDTO.owner()));
        if (restaurantDTO.kitchenType() != null && !restaurantDTO.kitchenType()
                .equalsIgnoreCase(oldRestaurant.getKitchenType()))
            oldRestaurant.setKitchenType(restaurantDTO.kitchenType());
        if (restaurantDTO.addressDTO() != null) {
            Address oldAaddress = oldRestaurant.getAddress();
            Address newAddress = Address.toAddressFromDTO(restaurantDTO.addressDTO());

            if (!newAddress.getStreet().equalsIgnoreCase(oldAaddress.getStreet()))
                oldAaddress.setStreet(newAddress.getStreet());
            if (!newAddress.getCity().equalsIgnoreCase(oldAaddress.getCity()))
                oldAaddress.setCity(newAddress.getCity());
            if (!newAddress.getState().equalsIgnoreCase(oldAaddress.getState()))
                oldAaddress.setState(newAddress.getState());
            if (!newAddress.getZipCode().equalsIgnoreCase(oldAaddress.getZipCode()))
                oldAaddress.setZipCode(newAddress.getZipCode());
        }
        if (restaurantDTO.openingHours() != null && !restaurantDTO.openingHours()
                .equalsIgnoreCase(oldRestaurant.getOpeningHours())) {
            oldRestaurant.setOpeningHours(restaurantDTO.openingHours());
        }

        return this.restaurantGateway.updateRestaurant(oldRestaurant);
    }
}
