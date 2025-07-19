package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class FindUserByLoginUseCase {

    IUserGateway userGateway;

    private FindUserByLoginUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindUserByLoginUseCase create(IUserGateway userGateway) {
        return new FindUserByLoginUseCase(userGateway);
    }

    public User execute(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login do usuário não pode ser nulo ou vazio");
        }
        return this.userGateway.findByLogin(login.trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o login: " + login));
    }
}
