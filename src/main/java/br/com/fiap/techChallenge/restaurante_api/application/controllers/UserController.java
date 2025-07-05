package br.com.fiap.techChallenge.restaurante_api.application.controllers;

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
