package br.com.fiap.techChallenge.restaurante_api.domain.gateway.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserGateway {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);

    User findById(UUID id);

    User createUser(User user);

    User updateUser(User user);

    User updatePassword(User user, String oldPassword);

    void deleteUser(UUID id);

}
