package br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD;

import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Usuario;
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
