package br.com.fiap.techChallenge.restaurante_api.application.usecases.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UpdatePasswordUserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.BusinessException;

import java.time.LocalDateTime;

public class FindPasswordUseCase {

    IUserGateway userGateway;

    private FindPasswordUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public static FindPasswordUseCase create(IUserGateway userGateway) {
        return new FindPasswordUseCase(userGateway);
    }

    public User execute(UpdatePasswordUserDTO userDTO) throws IllegalArgumentException {
        if (userDTO == null || userDTO.email() == null || userDTO.login() == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }
        User userUpdate = this.userGateway.findByLogin(userDTO.login().trim())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o login: " + userDTO.login()));

        if (!userDTO.oldPassword().equals(userUpdate.getPassword())) {
            throw new BusinessException("Senha atual incorreta");
        }

        userUpdate.setPassword(userDTO.newPassword());
        userUpdate.setLastChange(LocalDateTime.now());

        return this.userGateway.updateUser(userUpdate);
    }
}
