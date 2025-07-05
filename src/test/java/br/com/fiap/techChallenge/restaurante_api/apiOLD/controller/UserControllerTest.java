package br.com.fiap.techChallenge.restaurante_api.apiOLD.controller;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller.UserController;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        UserResponseDTO mockResponse = new UserResponseDTO();

        UserService userService = mock(UserService.class);
        when(userService.getUserById(id)).thenReturn(mockResponse);

        UserController controller = new UserController(userService);
        ResponseEntity<UserResponseDTO> response = controller.getUserById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
    }
}
