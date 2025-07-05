package br.com.fiap.techChallenge.restaurante_api.core.usecases;

import br.com.fiap.techChallenge.restaurante_api.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.CreateUserUseCase;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.application.repositories.IUserRepository;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
        UserType userType = UserType.CLIENTE;
        AddressDTO addressDTO = new AddressDTO("Rua A", "Cidade B", "Estado C", "12345-678");
        User newUser = User.create(name, email, login, password, userType, addressDTO.parser());
        NewUserDTO newUserDTO = new NewUserDTO(name, email, login, password, userType, addressDTO);
        IUserRepository iUserRepository = mock(IUserRepository.class);

        when(iUserRepository.searchByEmail(anyString())).thenReturn(Optional.empty());
        when(iUserRepository.searchByLogin(anyString())).thenReturn(Optional.empty());
        when(iUserRepository.include(any())).thenReturn(newUser);

        final User user = CreateUserUseCase.create(iUserRepository).execute(newUserDTO);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(login, user.getLogin());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertNull(user.getId());
    }
}