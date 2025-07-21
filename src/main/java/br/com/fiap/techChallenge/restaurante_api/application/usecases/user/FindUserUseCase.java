package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class FindUserUseCase {

    IUserGateway userGateway;

    private FindUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindUserUseCase create(IUserGateway userGateway) {
        return new FindUserUseCase(userGateway);
    }

    public User execute(UserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null || userDTO.id() == null || userDTO.name() == null || userDTO.email() == null || userDTO.userType() == null ||
                userDTO.addressDTO() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }
        this.userGateway.findByLogin(userDTO.login().trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o login: " + userDTO.login()));

        final User user = User.create(userDTO.id(), userDTO.name(), userDTO.email(), userDTO.login(), userDTO.password(),
                userDTO.userType(), userDTO.createDate(), userDTO.lastChange(), userDTO.addressDTO().parser());

        return this.userGateway.updateUser(user);
    }
}
