package br.com.fiap.techChallenge.restaurante_api.core.interfaces;

import br.com.fiap.techChallenge.restaurante_api.core.entities.User;

import java.util.Optional;

public interface IUserGateway {
    Optional<User> searchByEmail(String email);

    User include(User user);

    Optional<User> searchByLogin(String login);
}
