package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = User.create(UUID.randomUUID(), "Nome", null, "login", "senha", UserType.CLIENT, null, null, null);
            user.setEmail(null);
        });
        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            User user = User.create(UUID.randomUUID(), "Nome", "emailinvalido", "login", "senha", UserType.CLIENT, null, null, null);
            user.setEmail("emailinvalido");
        });
        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    void shouldCreateUserSuccessfullyWithAllFields() {
        UUID id = UUID.randomUUID();
        Address address = Address.create("Rua A", "Cidade B", "Estado C", "12345-678");
        LocalDateTime now = LocalDateTime.now();

        User user = User.create(id, "Nome", "email@valido.com", "login", "senha", UserType.CLIENT, now, now, address);

        assertNotNull(user);
        assertEquals("Nome", user.getName());
        assertEquals("email@valido.com", user.getEmail());
        assertEquals("login", user.getLogin());
        assertEquals("senha", user.getPassword());
        assertEquals(UserType.CLIENT, user.getUserType());
        assertEquals(address, user.getAddress());
        assertEquals(now, user.getCreateDate());
        assertEquals(now, user.getLastChange());
    }

    @Test
    void shouldConvertUserEntityToUserSuccessfully() {
        UUID id = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setName("Nome");
        userEntity.setEmail("email@valido.com");
        userEntity.setLogin("login");
        userEntity.setPassword("senha");
        userEntity.setUserType(UserTypeEnum.CLIENT);
        userEntity.setCreateDate(LocalDateTime.now());
        userEntity.setLastChange(LocalDateTime.now());
        userEntity.setAddressEntity(new AddressEntity());

        User user = User.toUser(userEntity);

        assertNotNull(user);
        assertEquals("Nome", user.getName());
        assertEquals("email@valido.com", user.getEmail());
        assertEquals("login", user.getLogin());
        assertEquals("senha", user.getPassword());
        assertEquals(UserType.CLIENT, user.getUserType());
    }

    @Test
    void shouldConvertUserDTOToUserSuccessfully() {
        UUID id = UUID.randomUUID();
        AddressDTO addressDTO = new AddressDTO("Rua A", "Cidade B", "Estado C", "12345-678");
        UserDTO userDTO = new UserDTO(id, "Nome", "email@valido.com", "login", "senha", UserType.CLIENT, addressDTO, LocalDateTime.now(), LocalDateTime.now());

        User user = User.toUserFromDTO(userDTO);

        assertNotNull(user);
        assertEquals("Nome", user.getName());
        assertEquals("email@valido.com", user.getEmail());
        assertEquals("login", user.getLogin());
        assertEquals("senha", user.getPassword());
        assertEquals(UserType.CLIENT, user.getUserType());
        assertNotNull(user.getAddress());
    }

    @Test
    void shouldConvertListOfUserEntitiesToListOfUsers() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(UUID.randomUUID());
        userEntity1.setName("User1");
        userEntity1.setEmail("user1@email.com");
        userEntity1.setLogin("user1");
        userEntity1.setPassword("senha1");
        userEntity1.setUserType(br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum.CLIENT);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(UUID.randomUUID());
        userEntity2.setName("User2");
        userEntity2.setEmail("user2@email.com");
        userEntity2.setLogin("user2");
        userEntity2.setPassword("senha2");
        userEntity2.setUserType(br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum.CLIENT);

        List<User> users = User.toUser(List.of(userEntity1, userEntity2));

        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getName());
        assertEquals("User2", users.get(1).getName());
    }

    @Test
    void shouldConvertListOfUserDTOsToListOfUsers() {
        UserDTO userDTO1 = new UserDTO(UUID.randomUUID(), "User1", "user1@email.com", "user1", "senha1", UserType.CLIENT, null, null, null);
        UserDTO userDTO2 = new UserDTO(UUID.randomUUID(), "User2", "user2@email.com", "user2", "senha2", UserType.CLIENT, null, null, null);

        List<User> users = User.toUserFromDTO(List.of(userDTO1, userDTO2));

        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getName());
        assertEquals("User2", users.get(1).getName());
    }
}