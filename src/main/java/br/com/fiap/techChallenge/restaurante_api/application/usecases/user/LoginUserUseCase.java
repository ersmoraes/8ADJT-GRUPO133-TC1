package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import java.util.Optional;

public class LoginUserUseCase {

    IUserGateway userGateway;

    private LoginUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static LoginUserUseCase create(IUserGateway userGateway) {
        return new LoginUserUseCase(userGateway);
    }

    public String execute(String login, String password) throws IllegalArgumentException {
        if (login == null || password == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }
        this.userGateway.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o login: " + login));

        Optional<User> usuario = this.userGateway.findByLoginAndPassword(login, password);
        if (usuario.isEmpty()) {
            return "Usuario ou senha invalidos!";
        }
        return "Login Efetuado com sucesso!";

    }
}
