package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FindAllUserUseCase {

    IUserGateway userGateway;

    private FindAllUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindAllUserUseCase create(IUserGateway userGateway) {
        return new FindAllUserUseCase(userGateway);
    }

    public Page<User> execute(Pageable pageable) {
        return this.userGateway.findAll(pageable);
    }
}
