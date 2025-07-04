package br.com.fiap.techChallenge.restaurante_api.infrastructure.presenters;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;

public class UserPresenter {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                AddressPresenter.toDTO(user.getAddress()),
                user.getLogin(),
                user.getUserType(),
                user.getCreateDate()
        );
    }
}
