package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
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
        userMock.setUserType(UserTypeEnum.PROPRIETARIO);
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
}