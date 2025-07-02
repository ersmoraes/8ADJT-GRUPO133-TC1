package br.com.fiap.techChallenge.restaurante_api.core.usecases;

import br.com.fiap.techChallenge.restaurante_api.core.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.core.entities.User;
import br.com.fiap.techChallenge.restaurante_api.core.interfaces.IUserGateway;

public class CreateUserUseCase {

    IUserGateway userGateway;

    private CreateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static CreateUserUseCase create(IUserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    public User execute(NewUserDTO newUserDTO) {
        if (newUserDTO == null || newUserDTO.name() == null || newUserDTO.email() == null || newUserDTO.userType() == null ||
                newUserDTO.address() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }

        this.userGateway.searchByEmail(newUserDTO.email().trim())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o email: " + newUserDTO.email());
                });
        this.userGateway.searchByLogin(newUserDTO.login().trim())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o login: " + newUserDTO.login());
                });

        final User user = User.create(newUserDTO.name(), newUserDTO.email(), newUserDTO.login(), newUserDTO.password(),
                newUserDTO.userType(), newUserDTO.address().parser());

        return this.userGateway.include(user);
    }
}
