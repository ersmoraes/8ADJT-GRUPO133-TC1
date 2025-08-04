package br.com.fiap.techChallenge.restaurante_api.core.usecases;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.user.CreateUserUseCase;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateUserUseCaseTest {

    @DisplayName("Cria usuário com sucesso")
    @Test
    void testExecuteOk() {
        String name = "Joao";
        String email = "joao@gmail.com";
        String login = "joao123";
        String password = "senha123";
        UserType userType = UserType.CLIENT;
        AddressDTO addressDTO = new AddressDTO("Rua A", "Cidade B", "Estado C", "12345-678");
        User newUser = User.create(UUID.randomUUID(), name, email, login, password, userType, null, null, addressDTO.parser());
        NewUserDTO newUserDTO = new NewUserDTO(name, email, login, password, userType, addressDTO);
        IUserGateway iUserGateway = mock(IUserGateway.class);

        when(iUserGateway.findByEmail(anyString())).thenReturn(Optional.empty());
        when(iUserGateway.findByLogin(anyString())).thenReturn(Optional.empty());
        when(iUserGateway.createUser(any())).thenReturn(newUser);

        final User user = CreateUserUseCase.create(iUserGateway).execute(newUserDTO);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(login, user.getLogin());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertNotNull(user.getId());
    }
}