package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class SearchUserByLoginAndPasswordUseCase {

    IUserGateway userGateway;

    private SearchUserByLoginAndPasswordUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static SearchUserByLoginAndPasswordUseCase create(IUserGateway userGateway) {
        return new SearchUserByLoginAndPasswordUseCase(userGateway);
    }

    public User execute(String login, String password) {
        if (login == null || login.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Login e senha do usuário não podem ser nulos ou vazios!");
        }
        return this.userGateway.findByLoginAndPassword(login.trim(), password.trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o login: " + login));
    }
}
