package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;

public class CreateUserUseCase {

    IUserGateway userGateway;

    private CreateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static CreateUserUseCase create(IUserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    public User execute(NewUserDTO newUserDTO) throws IllegalArgumentException {
        if (newUserDTO == null || newUserDTO.name() == null || newUserDTO.email() == null || newUserDTO.login() == null
                || newUserDTO.userType() == null || newUserDTO.address() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }

        this.userGateway.findByEmail(newUserDTO.email().trim())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o email: " + newUserDTO.email());
                });
        this.userGateway.findByLogin(newUserDTO.login().trim())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o login: " + newUserDTO.login());
                });

        final User user = User.create(null, newUserDTO.name(), newUserDTO.email(), newUserDTO.login(), newUserDTO.password(),
                newUserDTO.userType(), null, null, newUserDTO.address().parser());

        return this.userGateway.createUser(user);
    }
}
