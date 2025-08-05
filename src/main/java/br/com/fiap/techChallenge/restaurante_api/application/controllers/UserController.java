package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.UserPresenter;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.user.*;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.UserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class UserController {

    IUserDataSource dataSource;
    IUserGateway userGateway;

    private UserController(IUserDataSource dataSource) {
        this.dataSource = dataSource;
        this.userGateway = UserGateway.create(dataSource);
    }

    public static UserController create(IUserDataSource dataSource) {
        return new UserController(dataSource);
    }

    public Page<UserDTO> findAll(Pageable pageable) throws IllegalArgumentException {
        var useCase = FindAllUserUseCase.create(userGateway);
        return useCase.execute(pageable).map(UserPresenter::toDTO);
    }

    public String login(String login, String password) throws IllegalArgumentException {
        var useCase = LoginUserUseCase.create(userGateway);
        try {
            return useCase.execute(login, password);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao realizar login: " + e.getMessage(), e);
        }
    }

    public UserDTO findByLoginAndPassword(String login, String password) throws IllegalArgumentException {
        var useCase = FindUserByLoginAndPasswordUseCase.create(userGateway);
        try {
            var user = useCase.execute(login, password);
            return UserPresenter.toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao buscar usuário por login: " + e.getMessage(), e);
        }
    }

    public UserDTO findByEmail(String email) throws IllegalArgumentException {
        var useCase = FindUserByEmailUseCase.create(userGateway);
        try {
            var user = useCase.execute(email);
            return UserPresenter.toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao buscar usuário por email: " + e.getMessage(), e);
        }
    }

    public UserDTO findByLogin(String login) throws IllegalArgumentException {
        var useCase = FindUserByLoginUseCase.create(userGateway);
        try {
            var user = useCase.execute(login);
            return UserPresenter.toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao buscar usuário por login: " + e.getMessage(), e);
        }
    }

    public UserDTO findById(UUID id) throws IllegalArgumentException {
        var useCase = FindUserByIdUseCase.create(userGateway);
        var user = useCase.execute(id);
        return UserPresenter.toDTO(user);
    }

    public UserDTO createUser(NewUserDTO newUserDTO) throws IllegalArgumentException {
        var useCase = CreateUserUseCase.create(userGateway);
        try {
            var user = useCase.execute(newUserDTO);
            return UserPresenter.toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao criar usuário: " + e.getMessage(), e);
        }
    }

    public UserDTO updateUser(UserDTO userDTO) throws IllegalArgumentException {
        var useCase = UpdateUserUseCase.create(userGateway);
        try {
            var user = useCase.execute(userDTO);
            return UserPresenter.toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    public UserDTO updatePassword(UserDTO userDTO, String oldPassword) throws IllegalArgumentException {
        var useCase = UpdatePasswordUserUseCase.create(userGateway);
        try {
            var user = useCase.execute(userDTO, oldPassword);
            return UserPresenter.toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao atualizar senha do usuário: " + e.getMessage(), e);
        }
    }

    public void deleteUser(UUID id) throws IllegalArgumentException {
        var useCase = DeleteUserUseCase.create(userGateway);
        try {
            useCase.execute(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
}
