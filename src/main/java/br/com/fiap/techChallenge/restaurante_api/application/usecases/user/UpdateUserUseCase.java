package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import java.time.LocalDateTime;

public class UpdateUserUseCase {

    IUserGateway userGateway;

    private UpdateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static UpdateUserUseCase create(IUserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
    }

    public User execute(UserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null || userDTO.id() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }

        User oldUser = this.userGateway.findById(userDTO.id());
        if (oldUser == null) {
            throw new IllegalArgumentException("Usuário não existe com o id: " + userDTO.id());
        }
        if (userDTO.email() != null && !oldUser.getEmail().equals(userDTO.email().trim())) {
            this.userGateway.findByEmail(userDTO.email().trim())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("Usuário já cadastrado com o email inserido: "
                                + userDTO.email());
                    });
        }

        if (userDTO.name() != null && !userDTO.name().equalsIgnoreCase(oldUser.getName()))
            oldUser.setName(userDTO.name());
        if (userDTO.email() != null && !userDTO.email().equalsIgnoreCase(oldUser.getEmail()))
            oldUser.setEmail(userDTO.email());
        if (userDTO.userType() != null && !userDTO.userType().name().equalsIgnoreCase(oldUser.getUserType().name()))
            oldUser.setUserType(userDTO.userType());
        if (userDTO.addressDTO() != null) {
            Address oldAaddress = oldUser.getAddress();
            Address newAddress = Address.toAddressFromDTO(userDTO.addressDTO());

            if (!newAddress.getStreet().equalsIgnoreCase(oldAaddress.getStreet()))
                oldAaddress.setStreet(newAddress.getStreet());
            if (!newAddress.getCity().equalsIgnoreCase(oldAaddress.getCity()))
                oldAaddress.setCity(newAddress.getCity());
            if (!newAddress.getState().equalsIgnoreCase(oldAaddress.getState()))
                oldAaddress.setState(newAddress.getState());
            if (!newAddress.getZipCode().equalsIgnoreCase(oldAaddress.getZipCode()))
                oldAaddress.setZipCode(newAddress.getZipCode());
        }
        oldUser.setLastChange(LocalDateTime.now());

        return this.userGateway.updateUser(oldUser);
    }
}
