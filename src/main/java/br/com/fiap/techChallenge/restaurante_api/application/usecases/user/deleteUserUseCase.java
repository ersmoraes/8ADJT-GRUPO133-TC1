package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import java.util.UUID;

public class deleteUserUseCase {

    IUserGateway userGateway;

    private deleteUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static deleteUserUseCase create(IUserGateway userGateway) {
        return new deleteUserUseCase(userGateway);
    }

    public void execute(UUID id) {
        this.userGateway.deleteUser(id);
    }
}
