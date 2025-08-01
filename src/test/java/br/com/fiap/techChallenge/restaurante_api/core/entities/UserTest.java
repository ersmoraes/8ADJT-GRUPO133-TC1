package br.com.fiap.techChallenge.restaurante_api.core.entities;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("Cria usuário com sucesso")
    @Test
    void testEstudanteOk() {
        String name = "Joao";
        String email = "joao@gmail.com";
        String login = "joao123";
        String password = "senha123";
        UserType userType = UserType.CLIENTE;
        Address address = Address.create("Rua A", "Cidade B", "Estado C", "12345-678");

        var user = User.create(UUID.randomUUID(), name, email, login, password, userType, null, null, address);

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
        assertEquals(userType.name(), user.getUserType().name());
    }
}