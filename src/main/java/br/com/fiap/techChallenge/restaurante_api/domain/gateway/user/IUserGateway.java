package br.com.fiap.techChallenge.restaurante_api.domain.gateway.user;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserGateway {
    Optional<User> searchByEmail(String email);

    Optional<User> searchByLogin(String login);

    User createUser(User user);

    Page<User> findAll(Pageable pageable);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    Optional<User> findByLoginAndPassword(String login, String password);

    User findById(UUID id);

}
