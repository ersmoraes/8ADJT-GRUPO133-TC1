package br.com.fiap.techChallenge.restaurante_api.domain.gateway.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserDataSource {
    UserDTO searchByEmail(String email);

    UserDTO searchByLogin(String login);

    UserDTO createUser(NewUserDTO dto);

    Page<UserDTO> findAll(Pageable pageable);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    Optional<UserDTO> findByLoginAndPassword(String login, String password);

    UserDTO findById(UUID id);
}
