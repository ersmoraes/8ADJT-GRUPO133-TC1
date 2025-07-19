package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class UpdatePasswordUserUseCase {

    IUserGateway userGateway;

    private UpdatePasswordUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static UpdatePasswordUserUseCase create(IUserGateway userGateway) {
        return new UpdatePasswordUserUseCase(userGateway);
    }

    public User execute(UserDTO userDTO, String oldPassword) throws IllegalArgumentException {
        if (userDTO == null || userDTO.id() == null || oldPassword == null || userDTO.password() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }

        User oldUser = this.userGateway.findById(userDTO.id());
        if (oldUser == null) {
            throw new IllegalArgumentException("Usuário não existe com o id: " + userDTO.id());
        }
        if (!oldUser.getPassword().equalsIgnoreCase(oldPassword)) {
            throw new IllegalArgumentException("Senha antiga inválida");
        }

        oldUser.setPassword(userDTO.password());

        return this.userGateway.updatePassword(oldUser, oldPassword);
    }
}
