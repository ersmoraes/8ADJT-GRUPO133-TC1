package br.com.fiap.techChallenge.restaurante_api.domain.gateway.user;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UpdatePasswordUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class UserGateway implements IUserGateway {
    private final IUserDataSource dataSource;

    private UserGateway(IUserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserGateway create(IUserDataSource dataSource) {
        return new UserGateway(dataSource);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.dataSource.findAll(pageable).map(User::toUserFromDTO);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Optional<UserDTO> userDTOOptional = this.dataSource.findByLoginAndPassword(login, password);
        if (userDTOOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário ou senha incorretos!");
        }
        UserDTO userDTO = userDTOOptional.get();
        return Optional.of(User.create(userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser()));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserDTO> userDTOOptional = this.dataSource.findByEmail(email);
        if (userDTOOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com o email: " + email);
        }
        UserDTO userDTO = userDTOOptional.get();
        return Optional.of(User.create(userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser()));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<UserDTO> userDTOOptional = this.dataSource.findByLogin(login);
        if (userDTOOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com o login: " + login);
        }
        UserDTO userDTO = userDTOOptional.get();
        return Optional.of(User.create(userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser()));
    }

    @Override
    public User findById(UUID id) {
        Optional<UserDTO> userDTOOptional = this.dataSource.findById(id);
        if (userDTOOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + id);
        }
        UserDTO userDTO = userDTOOptional.get();
        return User.create(userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser());
    }

    @Override
    public User createUser(User user) {
        NewUserDTO newUserDTO = new NewUserDTO(
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getUserType(),
                AddressDTO.toAddressDTO(user.getAddress())
        );
        UserDTO userDTO = dataSource.createUser(newUserDTO);
        return User.create(
                userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser());
    }

    @Override
    public User updateUser(User user) {
        UserDTO UserDTO = new UserDTO(
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
        UserDTO userDTO = dataSource.updateUser(UserDTO);
        return User.create(
                userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser());
    }

    @Override
    public User updatePassword(User user, String oldPassword) {
        UpdatePasswordUserDTO UserDTO = new UpdatePasswordUserDTO(
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                oldPassword
        );
        UserDTO userDTO = dataSource.updatePassword(UserDTO);
        return User.create(
                userDTO.name(),
                userDTO.email(),
                userDTO.login(),
                userDTO.password(),
                userDTO.userType(),
                userDTO.addressDTO().parser());
    }

    @Override
    public void deleteUser(UUID id) {
        this.dataSource.deleteUser(id);
    }
}
