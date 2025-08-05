package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    void shouldFindUserById() {
        UserEntity usuario = new UserEntity();
        usuario.setLogin("paulocesar");
        usuario.setPassword("123456");

        UserEntity savedUser = userRepository.save(usuario);
        Optional<UserEntity> found = userRepository.findById(savedUser.getId());

        assertTrue(found.isPresent());
        assertEquals("paulocesar", found.get().getLogin());
    }
}