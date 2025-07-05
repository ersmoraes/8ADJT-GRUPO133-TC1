package br.com.fiap.techChallenge.restaurante_api.application.repositories;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> searchByEmail(String email);

    Optional<User> searchByLogin(String login);

    User createUser(User user);

}
