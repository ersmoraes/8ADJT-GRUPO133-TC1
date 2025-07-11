package br.com.fiap.techChallenge.restaurante_api.application.usecases;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class SearchUserByLoginUseCase {

    IUserGateway userGateway;

    private SearchUserByLoginUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static SearchUserByLoginUseCase create(IUserGateway userGateway) {
        return new SearchUserByLoginUseCase(userGateway);
    }

    public User execute(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login do usuário não pode ser nulo ou vazio");
        }
        return this.userGateway.searchByLogin(login.trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o login: " + login));
    }
}
