package br.com.fiap.techChallenge.restaurante_api.domain.service;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;

import java.util.UUID;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO updateUser(UUID id, UserRequestDTO dto);
    void deleteUser(UUID id);
    void updatePassword(UUID userId, PasswordUpdateRequestDTO dto);
}
