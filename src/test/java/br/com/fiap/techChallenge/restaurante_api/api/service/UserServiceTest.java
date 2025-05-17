package br.com.fiap.techChallenge.restaurante_api.api.service;

import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import br.com.fiap.techChallenge.restaurante_api.domain.repository.UserRepository;
import br.com.fiap.techChallenge.restaurante_api.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserResponseDTOWhenUserExists() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setLogin("paulocesar");

        when(userRepository.findById(id)).thenReturn(Optional.of(usuario));

        UserResponseDTO response = userService.getUserById(id);

        assertEquals(id, response.getId());
        assertEquals("paulocesar", response.getLogin());
        assertTrue(response.getActive());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(id));
    }
}
