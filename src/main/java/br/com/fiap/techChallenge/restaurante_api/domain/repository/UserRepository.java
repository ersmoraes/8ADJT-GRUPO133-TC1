package br.com.fiap.techChallenge.restaurante_api.domain.repository;

import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Usuario, UUID> {
    Page<Usuario> findAll(Pageable pageable);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Optional<Usuario> findByLoginAndPassword(String login, String password);
}
