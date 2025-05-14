package br.com.fiap.techChallenge.restaurante_api.domain.repository;

import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
