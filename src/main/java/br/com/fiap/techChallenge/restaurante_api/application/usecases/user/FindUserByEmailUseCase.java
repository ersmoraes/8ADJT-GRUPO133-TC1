package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

public class FindUserByEmailUseCase {

    IUserGateway userGateway;

    private FindUserByEmailUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindUserByEmailUseCase create(IUserGateway userGateway) {
        return new FindUserByEmailUseCase(userGateway);
    }

    public User execute(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário não pode ser nulo ou vazio");
        }
        return this.userGateway.findByEmail(email.trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o email: " + email));
    }
}
