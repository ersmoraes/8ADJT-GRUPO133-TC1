package br.com.fiap.techChallenge.restaurante_api.api.service;

import br.com.fiap.techChallenge.restaurante_api.api.controller.UserController;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import br.com.fiap.techChallenge.restaurante_api.domain.repository.UserRepository;
import br.com.fiap.techChallenge.restaurante_api.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserController controller;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        controller = new UserController(userService);
    }

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        UserResponseDTO mockResponse = new UserResponseDTO();
        mockResponse.setId(id);
        mockResponse.setLogin("paulocesar");
        when(userService.getUserById(id)).thenReturn(mockResponse);

        ResponseEntity<UserResponseDTO> response = controller.getUserById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        assertEquals(mockResponse.getId(), response.getBody().getId());
        assertEquals(mockResponse.getLogin(), response.getBody().getLogin());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userService.getUserById(id)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> controller.getUserById(id));
    }
}
