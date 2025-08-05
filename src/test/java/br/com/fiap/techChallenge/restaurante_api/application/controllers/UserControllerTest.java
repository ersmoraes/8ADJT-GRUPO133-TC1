package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private IUserDataSource dataSource;
    private UserController controller;

    AddressDTO addressDTO = new AddressDTO("Rua", "Cidade", "Estado", "12345-678");

    @BeforeEach
    void setUp() {
        dataSource = mock(IUserDataSource.class);
        controller = UserController.create(dataSource);
    }

    @Test
    void shouldThrowExceptionWhenCreateUserWithNullDTO() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.createUser(null));
        assertTrue(exception.getMessage().contains("Erro ao criar usuário"));
    }

    @Test
    void shouldCreateUserWhenDataIsValid() {
        NewUserDTO newUserDTO = new NewUserDTO("Nome", "email@email.com", "login", "senha", UserType.OWNER, addressDTO);
        UserDTO userDTO = mock(UserDTO.class);
        when(dataSource.createUser(newUserDTO)).thenReturn(userDTO);

        UserDTO result = controller.createUser(newUserDTO);

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenFindByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> controller.findById(null));
    }

    @Test
    void shouldReturnUserDTOWhenFindByIdIsValid() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = mock(UserDTO.class);
        when(dataSource.findById(id)).thenReturn(Optional.of(userDTO));

        UserDTO result = controller.findById(id);

        assertNotNull(result);
    }

    @Test
    void shouldReturnUserDTOWhenFindByLoginIsValid() {
        UserDTO userDTO = mock(UserDTO.class);
        when(dataSource.findByLogin("login")).thenReturn(Optional.of(userDTO));

        UserDTO result = controller.findByLogin("login");

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenLoginFails() {
        when(dataSource.findByLoginAndPassword("login", "wrong")).thenThrow(new IllegalArgumentException("Credenciais inválidas"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.login("login", "wrong"));
        assertTrue(exception.getMessage().contains("Erro ao realizar login"));
    }

    @Test
    void shouldReturnSuccessMessageWhenLoginSucceeds() {
        UserDTO userDTO = mock(UserDTO.class);
        when(dataSource.findByLogin("login")).thenReturn(Optional.of(userDTO));
        when(dataSource.findByLoginAndPassword("login", "senha")).thenReturn(Optional.of(userDTO));

        String result = controller.login("login", "senha");

        assertNotNull(result);
    }

    @Test
    void shouldDeleteUserWithoutException() {
        UUID id = UUID.randomUUID();

        assertDoesNotThrow(() -> controller.deleteUser(id));
        verify(dataSource, times(1)).deleteUser(id);
    }
}