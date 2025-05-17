package br.com.fiap.techChallenge.restaurante_api.api.repository;

import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import br.com.fiap.techChallenge.restaurante_api.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserById() {
        Usuario usuario = new Usuario();
        usuario.setLogin("paulocesar");
        usuario.setPassword("123456");

        Usuario savedUser = userRepository.save(usuario);
        Optional<Usuario> found = userRepository.findById(savedUser.getId());

        assertTrue(found.isPresent());
        assertEquals("paulocesar", found.get().getLogin());
    }
}
