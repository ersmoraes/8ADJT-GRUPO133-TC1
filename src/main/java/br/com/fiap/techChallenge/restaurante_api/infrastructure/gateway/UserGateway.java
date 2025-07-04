package br.com.fiap.techChallenge.restaurante_api.infrastructure.gateway;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.application.repositories.IDataSource;
import br.com.fiap.techChallenge.restaurante_api.application.repositories.IUserRepository;

import java.util.Optional;

public class UserGateway { // implements IUserRepository
//    IDataSource dataSource;
//
//    private UserGateway(IDataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public static UserGateway create(IDataSource dataSource) {
//        return new UserGateway(dataSource);
//    }
//
//    @Override
//    public Optional<User> searchByEmail(String email) {
//        var userByEmail = dataSource.searchByEmail(email);
//
//        if (userByEmail == null) {
//            throw new IllegalArgumentException("Usuário não encontrado com o email: " + email);
//        }
//        return Optional.of(User.create(userByEmail.name(), userByEmail.email(), userByEmail.login(), null,
//                userByEmail.userType(), userByEmail.addressDTO().parser()));
//    }
//
//    @Override
//    public Optional<User> searchByLogin(String login) {
//        var userByLogin = dataSource.searchByLogin(login);
//
//        if (userByLogin == null) {
//            throw new IllegalArgumentException("Usuário não encontrado com o login: " + login);
//        }
//        return Optional.of(User.create(userByLogin.name(), userByLogin.email(), userByLogin.login(), null,
//                userByLogin.userType(), userByLogin.addressDTO().parser()));
//    }
//
//    @Override
//    public User createUser(User user) {
//        final NewUserDTO newUserDTO = new NewUserDTO(user.getName(), user.getEmail(), user.getLogin(), user.getPassword(),
//                user.getUserType(), new AddressDTO(user.getAddress().getStreet(), user.getAddress().getCity(),
//                user.getAddress().getState(), user.getAddress().getZipCode()));
//        final UserDTO userResponseDTO = dataSource.createUser(newUserDTO);
//
//        return User.create(userResponseDTO.name(), userResponseDTO.email(), userResponseDTO.login(), null,
//                userResponseDTO.userType(), userResponseDTO.addressDTO().parser());
//    }
}
