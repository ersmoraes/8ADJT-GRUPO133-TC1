package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.controllers.UserController;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRESTControllerTest {

    @Mock
    UserServiceImpl userService;
    AddressDTO addressDTO = new AddressDTO("Street", "City", "State", "12345-678");

    @Test
    void shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        UserDTO mockResponse = new UserDTO(
                id,
                "Paulo cesar",
                "paulo@gmail.com",
                "paulocesar",
                "123456",
                UserType.OWNER,
                addressDTO,
                null,
                null);

        when(userService.findById(id)).thenReturn(Optional.of(mockResponse));

        UserRESTController controller = new UserRESTController(userService);
        ResponseEntity<UserResponseDTO> response = controller.findById(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(mockResponse.id(), response.getBody().getId());
    }

    @Test
    void shouldReturnUnauthorizedWhenLoginCredentialsAreInvalid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("usuarioInvalido");
        loginRequestDTO.setPassword("senhaInvalida");
        UserDTO userDTO = mock(UserDTO.class);

        when(userService.findByLogin(loginRequestDTO.getLogin())).thenReturn(Optional.of(userDTO));
        when(userService.findByLoginAndPassword(loginRequestDTO.getLogin(), loginRequestDTO.getPassword()))
                .thenReturn(Optional.empty());

        UserRESTController controller = new UserRESTController(userService);
        ResponseEntity<String> response = controller.login(loginRequestDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Erro ao realizar login: Usuário ou senha inválidos!", response.getBody());
    }

    @Test
    void shouldReturnOkWhenLoginCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("usuarioValido");
        loginRequestDTO.setPassword("senhaValida");
        UserDTO userDTO = mock(UserDTO.class);

        when(userService.findByLogin(loginRequestDTO.getLogin())).thenReturn(Optional.of(userDTO));
        when(userService.findByLoginAndPassword(loginRequestDTO.getLogin(), loginRequestDTO.getPassword()))
                .thenReturn(Optional.of(userDTO));

        UserRESTController controller = new UserRESTController(userService);
        ResponseEntity<String> response = controller.login(loginRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login realizado com sucesso!", response.getBody());
    }

    @Test
    void shouldReturnNoContentWhenUserIsDeleted() {
        UUID id = UUID.randomUUID();
        UserRESTController controller = new UserRESTController(userService);

        ResponseEntity<Void> response = controller.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldUpdateUserAndReturnUpdatedUserResponseDTO() {
        UUID id = UUID.randomUUID();
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Novo Nome");
        userRequestDTO.setEmail("novo@email.com");
        userRequestDTO.setLogin("novologin");
        userRequestDTO.setPassword("novasenha");
        userRequestDTO.setUserType(UserType.CLIENT);
        UserDTO updatedUserDTO = new UserDTO(id, "Novo Nome", "novo@email.com", "novologin",
                "novasenha", UserType.CLIENT, addressDTO, null, null);

        when(userService.updateUser(any(UserDTO.class))).thenReturn(updatedUserDTO);
        when(userService.findById(any())).thenReturn(Optional.of(updatedUserDTO));

        UserRESTController controller = new UserRESTController(userService);
        ResponseEntity<UserResponseDTO> response = controller.updateUser(id, userRequestDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Novo Nome", response.getBody().getName());
        assertEquals("novologin", response.getBody().getLogin());
    }

    @Test
    void shouldCreateUserAndReturnUserResponseDTO() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Fulano");
        userRequestDTO.setEmail("fulano@email.com");
        userRequestDTO.setLogin("fulanologin");
        userRequestDTO.setPassword("senha");
        userRequestDTO.setUserType(UserType.CLIENT);
        UserDTO createdUserDTO = new UserDTO(UUID.randomUUID(), "Fulano", "fulano@email.com", "fulanologin",
                "senha", UserType.CLIENT, addressDTO, null, null);

        when(userService.createUser(any(NewUserDTO.class))).thenReturn(createdUserDTO);

        UserRESTController controller = new UserRESTController(userService);
        ResponseEntity<UserResponseDTO> response = controller.createUser(userRequestDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Fulano", response.getBody().getName());
        assertEquals("fulanologin", response.getBody().getLogin());
    }
}