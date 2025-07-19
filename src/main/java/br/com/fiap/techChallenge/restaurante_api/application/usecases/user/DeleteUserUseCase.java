package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import java.util.UUID;

public class DeleteUserUseCase {

    IUserGateway userGateway;

    private DeleteUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static DeleteUserUseCase create(IUserGateway userGateway) {
        return new DeleteUserUseCase(userGateway);
    }

    public void execute(UUID id) {
        this.userGateway.deleteUser(id);
    }
}
