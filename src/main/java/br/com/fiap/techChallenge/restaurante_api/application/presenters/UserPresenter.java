package br.com.fiap.techChallenge.restaurante_api.application.presenters;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;

public class UserPresenter {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getUserType(),
                AddressDTO.toAddressDTO(user.getAddress()),
                user.getCreateDate(),
                user.getLastChange()
        );
    }
}
