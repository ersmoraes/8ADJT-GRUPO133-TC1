package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UpdatePasswordUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        UserEntity userMock = new UserEntity();
        userMock.setId(id);
        userMock.setLogin("paulocesar");
        userMock.setUserType(UserTypeEnum.OWNER);
        userMock.setAddressEntity(AddressEntity.builder().build());
        Optional<UserEntity> userEntity = Optional.of(userMock);
        when(userRepository.findById(id)).thenReturn(userEntity);

        UserDTO response = userService.findById(id).get();

        assertNotNull(response);
        assertEquals(userMock.getId(), response.id());
        assertEquals(userMock.getLogin(), response.login());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userService.findById(id));
    }

    @Test
    void shouldReturnErrorMessageWhenLoginCredentialsAreInvalid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("usuarioInvalido");
        loginRequestDTO.setPassword("senhaInvalida");

        when(userRepository.findByLoginAndPassword(loginRequestDTO.getLogin(), loginRequestDTO.getPassword()))
                .thenReturn(Optional.empty());

        String result = userService.login(loginRequestDTO);

        assertEquals("Usuario ou senha invalidos!", result);
    }

    @Test
    void shouldReturnSuccessMessageWhenLoginCredentialsAreValid() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLogin("usuarioValido");
        loginRequestDTO.setPassword("senhaValida");
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("usuarioValido");
        userEntity.setPassword("senhaValida");

        when(userRepository.findByLoginAndPassword(loginRequestDTO.getLogin(), loginRequestDTO.getPassword()))
                .thenReturn(Optional.of(userEntity));

        String result = userService.login(loginRequestDTO);

        assertEquals("Login Efetuado com sucesso!", result);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingPasswordForNonExistentUser() {
        UpdatePasswordUserDTO updatePasswordUserDTO = new UpdatePasswordUserDTO("email@email.com",
                "loginNaoExiste", "naoExiste", "novaSenha");
        when(userRepository.findByLogin(updatePasswordUserDTO.login()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updatePassword(updatePasswordUserDTO));
    }

    @Test
    void shouldUpdatePasswordSuccessfullyForExistingUser() {
        UpdatePasswordUserDTO updatePasswordUserDTO = new UpdatePasswordUserDTO("email@email.com",
                "usuarioExistente", "newPassword", "oldPassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("usuarioExistente");
        userEntity.setPassword("oldPassword");
        when(userRepository.findByLogin(updatePasswordUserDTO.login()))
                .thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO result = userService.updatePassword(updatePasswordUserDTO);

        assertEquals("newPassword", result.password());
        assertNotNull(result.lastChange());
    }
}