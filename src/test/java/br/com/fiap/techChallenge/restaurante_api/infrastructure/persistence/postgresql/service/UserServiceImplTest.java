package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UpdatePasswordUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.IUserRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserRepository userService;
    @Mock
    private IUserRepository userRepository;

    AddressDTO addressDTO = new AddressDTO("Rua", "Cidade", "Estado", "12345-678");

    @Test
    void shouldReturnPageOfUsersWhenFindAllIsCalled() {
        Pageable pageable = mock(Pageable.class);
        Page<UserEntity> userEntities = mock(Page.class);
        when(userRepository.findAll(pageable)).thenReturn(userEntities);
        when(userEntities.map(any())).thenReturn(mock(Page.class));

        Page<UserDTO> result = userService.findAll(pageable);

        assertNotNull(result);
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnUserDTOWhenFindByIdIsCalledWithValidId() {
        UUID id = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        Optional<UserDTO> result = userService.findById(id);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnUserDTOWhenFindByEmailIsCalledWithValidId() {
        String email = "email@email.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        Optional<UserDTO> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldReturnUserDTOWhenFindByLoginIsCalledWithValidId() {
        String login = "loginValido";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));

        Optional<UserDTO> result = userService.findByLogin(login);

        assertTrue(result.isPresent());
        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    void shouldCreateUserSuccessfullyWhenAllFieldsAreValid() {
        NewUserDTO newUserDTO = new NewUserDTO("Nome", "email@email.com", "login", "senha", UserType.OWNER, addressDTO);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO result = userService.createUser(newUserDTO);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "Nome", "email@email.com", "login", "senha", UserType.OWNER, addressDTO, LocalDateTime.now(), null);
        UserEntity userEntity = new UserEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO result = userService.updateUser(userDTO);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID id = UUID.randomUUID();

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldUpdatePasswordSuccessfully() {
        UpdatePasswordUserDTO updatePasswordUserDTO = new UpdatePasswordUserDTO("email@email.com", "login", "newPassword", "oldPassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("login");
        userEntity.setPassword("oldPassword");
        when(userRepository.findByLogin(updatePasswordUserDTO.login())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO result = userService.updatePassword(updatePasswordUserDTO);

        assertNotNull(result);
        assertEquals("newPassword", result.password());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatePasswordUserNotFound() {
        UpdatePasswordUserDTO updatePasswordUserDTO = new UpdatePasswordUserDTO("email@email.com", "login", "newPassword", "oldPassword");
        when(userRepository.findByLogin(updatePasswordUserDTO.login())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updatePassword(updatePasswordUserDTO));
    }

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