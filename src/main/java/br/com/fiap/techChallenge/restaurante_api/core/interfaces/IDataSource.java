package br.com.fiap.techChallenge.restaurante_api.core.interfaces;

import br.com.fiap.techChallenge.restaurante_api.core.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.core.dto.UserDTO;

public interface IDataSource {
    UserDTO searchByEmail(String email);

    UserDTO searchByLogin(String login);

    UserDTO include(NewUserDTO user);
}
