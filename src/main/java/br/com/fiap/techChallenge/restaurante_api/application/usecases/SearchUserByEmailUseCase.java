package br.com.fiap.techChallenge.restaurante_api.application.usecases;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class SearchUserByEmailUseCase {

    IUserGateway userGateway;

    private SearchUserByEmailUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static SearchUserByEmailUseCase create(IUserGateway userGateway) {
        return new SearchUserByEmailUseCase(userGateway);
    }

    public User execute(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário não pode ser nulo ou vazio");
        }
        return this.userGateway.searchByEmail(email.trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o email: " + email));
    }
}
