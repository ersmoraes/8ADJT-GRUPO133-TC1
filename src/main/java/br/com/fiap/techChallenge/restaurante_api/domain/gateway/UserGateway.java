package br.com.fiap.techChallenge.restaurante_api.domain.gateway;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class UserGateway implements IUserGateway {
    private IDataSource dataSource;

    private UserGateway(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserGateway create(IDataSource dataSource) {
        return new UserGateway(dataSource);
    }

    @Override
    public Optional<User> searchByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> searchByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsByLogin(String login) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return Optional.empty();
    }

    @Override
    public User findById(UUID id) {
        UserDTO userDTO = this.dataSource.findById(id);
        if (userDTO == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + id);
        }
        return User.create(userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser());
    }
}
