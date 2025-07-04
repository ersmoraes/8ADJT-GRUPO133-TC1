package br.com.fiap.techChallenge.restaurante_api.application.repositories;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.UserDTO;

public interface IDataSource {
    UserDTO searchByEmail(String email);

    UserDTO searchByLogin(String login);

    UserDTO createUser(NewUserDTO dto);
}
