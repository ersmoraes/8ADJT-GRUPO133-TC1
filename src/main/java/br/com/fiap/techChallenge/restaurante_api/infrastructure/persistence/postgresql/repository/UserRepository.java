package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Page<UserEntity> findAll(Pageable pageable);
    Optional<UserEntity> findByLoginAndPassword(String login, String password);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByLogin(String login);
}
