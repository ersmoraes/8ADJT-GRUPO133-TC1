package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.UserPresenter;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.user.SearchUserByIdUseCase;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.UserGateway;

import java.util.UUID;

public class UserController {

    IUserDataSource dataSource;

    private UserController(IUserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserController create(IUserDataSource dataSource) {
        return new UserController(dataSource);
    }
//
//    public UserDTO createUser(NewUserDTO newUserDTO) throws IllegalArgumentException {
//        var useCase = CreateUserUseCase.create(userGateway);
//        try {
//            var user = useCase.execute(newUserDTO);
//            return UserPresenter.toDTO(user);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Erro ao criar usuário: " + e.getMessage(), e);
//        }
//    }
//
//    public UserDTO searchUserByEmail(String email) throws IllegalArgumentException {
//        var useCase = SearchUserByEmailUseCase.create(userGateway);
//        try {
//            var user = useCase.execute(email);
//            return UserPresenter.toDTO(user);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Erro ao buscar usuário por email: " + e.getMessage(), e);
//        }
//    }
//
//    public UserDTO searchUserByLogin(String login) throws IllegalArgumentException {
//        var useCase = SearchUserByLoginUseCase.create(userGateway);
//        try {
//            var user = useCase.execute(login);
//            return UserPresenter.toDTO(user);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Erro ao buscar usuário por login: " + e.getMessage(), e);
//        }
//    }

    public UserDTO findById(UUID id) throws IllegalArgumentException {
        IUserGateway userGateway = UserGateway.create(dataSource);
        var useCase = SearchUserByIdUseCase.create(userGateway);
        var user = useCase.execute(id);
        return UserPresenter.toDTO(user);
    }
}
