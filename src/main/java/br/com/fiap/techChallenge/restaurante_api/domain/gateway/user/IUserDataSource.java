package br.com.fiap.techChallenge.restaurante_api.domain.gateway.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UpdatePasswordUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserDataSource {
    Page<UserDTO> findAll(Pageable pageable);

    Optional<UserDTO> findByLoginAndPassword(String login, String password);

    Optional<UserDTO> findByEmail(String email);

    Optional<UserDTO> findByLogin(String login);

    Optional<UserDTO> findById(UUID id);

    UserDTO createUser(NewUserDTO dto);

    UserDTO updateUser(UserDTO dto);

    UserDTO updatePassword(UpdatePasswordUserDTO dto);

    void deleteUser(UUID id);
}
