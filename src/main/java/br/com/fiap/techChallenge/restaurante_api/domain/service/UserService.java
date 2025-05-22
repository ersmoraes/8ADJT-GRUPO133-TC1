package br.com.fiap.techChallenge.restaurante_api.domain.service;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Page<Usuario> findAll(Pageable pageable);
    String login(LoginRequestDTO loginDTO);
    UserResponseDTO getUserById(UUID id);
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO updateUser(UUID id, UserRequestDTO dto);
    void deleteUser(UUID id);
    void updatePassword(UUID userId, PasswordUpdateRequestDTO dto);
}
