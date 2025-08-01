package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRESTControllerTest {

    @InjectMocks
    UserRESTController controller;
    @Mock
    UserServiceImpl userService;

    @Test
    void shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        UserDTO mockResponse = new UserDTO(
                id,
                "Paulo cesar",
                "paulo@gmail.com",
                "paulocesar",
                "123456",
                UserType.PROPRIETARIO,
                null,
                null,
                null);

        when(userService.findById(id)).thenReturn(Optional.of(mockResponse));

        UserRESTController controller = new UserRESTController(userService);
        ResponseEntity<UserResponseDTO> response = controller.findById(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(mockResponse.id(), response.getBody().getId());
    }
}