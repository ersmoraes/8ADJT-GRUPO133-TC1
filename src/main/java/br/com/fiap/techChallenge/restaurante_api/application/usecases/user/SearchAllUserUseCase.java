package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SearchAllUserUseCase {

    IUserGateway userGateway;

    private SearchAllUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static SearchAllUserUseCase create(IUserGateway userGateway) {
        return new SearchAllUserUseCase(userGateway);
    }

    public Page<User> execute(Pageable pageable) {
        return this.userGateway.findAll(pageable);
    }
}
