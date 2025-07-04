package br.com.fiap.techChallenge.restaurante_api.infrastructure.db.repository;

import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Usuario;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findAll(Pageable pageable);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Optional<User> findByLoginAndPassword(String login, String password);
}
