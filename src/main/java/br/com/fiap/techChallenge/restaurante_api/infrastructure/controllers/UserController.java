package br.com.fiap.techChallenge.restaurante_api.infrastructure.controllers;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.gateway.UserGateway;
import br.com.fiap.techChallenge.restaurante_api.application.repositories.IDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.presenters.UserPresenter;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.CreateUserUseCase;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.SearchUserByEmailUseCase;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.SearchUserByLoginUseCase;

public class UserController {
//    IDataSource dataSource;
//
//    private UserController(IDataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public static UserController create(IDataSource dataSource) {
//        return new UserController(dataSource);
//    }
//
//    public UserDTO createUser(NewUserDTO newUserDTO) throws IllegalArgumentException {
//        var userGateway = UserGateway.create(this.dataSource);
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
//        var userGateway = UserGateway.create(this.dataSource);
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
//        var userGateway = UserGateway.create(this.dataSource);
//        var useCase = SearchUserByLoginUseCase.create(userGateway);
//        try {
//            var user = useCase.execute(login);
//            return UserPresenter.toDTO(user);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Erro ao buscar usuário por login: " + e.getMessage(), e);
//        }
//    }
}
