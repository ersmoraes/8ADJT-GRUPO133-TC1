package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import java.util.UUID;

public class FindUserByIdUseCase {

    IUserGateway userGateway;

    private FindUserByIdUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindUserByIdUseCase create(IUserGateway userGateway) {
        return new FindUserByIdUseCase(userGateway);
    }

    public User execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        User userById = this.userGateway.findById(id);
        if (userById == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o id informado");
        }
        return userById;
    }
}
