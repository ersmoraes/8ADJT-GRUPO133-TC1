package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class UpdateUserUseCase {

    IUserGateway userGateway;

    private UpdateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static UpdateUserUseCase create(IUserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
    }

    public User execute(UserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null || userDTO.name() == null || userDTO.email() == null || userDTO.login() == null
                || userDTO.userType() == null || userDTO.addressDTO() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }

        User oldUser = this.userGateway.findByLogin(userDTO.login().trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não existe com o login: " + userDTO.login()));
        if (!oldUser.getEmail().equals(userDTO.email().trim())) {
            this.userGateway.findByEmail(userDTO.email().trim())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("Usuário já cadastrado com o email inserido: "
                                + userDTO.email());
                    });
        }

        final User user = User.create(userDTO.name(), userDTO.email(), userDTO.login(), userDTO.password(),
                userDTO.userType(), userDTO.addressDTO().parser());

        return this.userGateway.updateUser(user);
    }
}
