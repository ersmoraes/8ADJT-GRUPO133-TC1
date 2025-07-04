package br.com.fiap.techChallenge.restaurante_api.application.usecases;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.application.repositories.IUserRepository;

public class SearchUserByLoginUseCase {

    IUserRepository userGateway;

    private SearchUserByLoginUseCase(IUserRepository userGateway) {
        this.userGateway = userGateway;
    }

    public static SearchUserByLoginUseCase create(IUserRepository userGateway) {
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
