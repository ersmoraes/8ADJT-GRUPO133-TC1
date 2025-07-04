package br.com.fiap.techChallenge.restaurante_api.application.usecases;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.repositories.IUserRepository;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;

public class CreateUserUseCase {

    IUserRepository userRepository;

    private CreateUserUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static CreateUserUseCase create(IUserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    public User execute(NewUserDTO newUserDTO) throws IllegalArgumentException {
        if (newUserDTO == null || newUserDTO.name() == null || newUserDTO.email() == null || newUserDTO.userType() == null ||
                newUserDTO.address() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }

        this.userRepository.searchByEmail(newUserDTO.email().trim())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o email: " + newUserDTO.email());
                });
        this.userRepository.searchByLogin(newUserDTO.login().trim())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Usuário já cadastrado com o login: " + newUserDTO.login());
                });

        final User user = User.create(newUserDTO.name(), newUserDTO.email(), newUserDTO.login(), newUserDTO.password(),
                newUserDTO.userType(), newUserDTO.address().parser());

        return this.userRepository.createUser(user);
    }
}
