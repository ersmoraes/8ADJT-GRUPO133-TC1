package br.com.fiap.techChallenge.restaurante_api.domain.repositoryOLD;

import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Page<Usuario> findAll(Pageable pageable);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    Optional<Usuario> findByLoginAndPassword(String login, String password);
}
