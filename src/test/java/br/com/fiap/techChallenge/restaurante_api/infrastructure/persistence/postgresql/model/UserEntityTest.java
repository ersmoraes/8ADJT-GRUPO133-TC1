package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class UserEntityTest {

    @Test
    void shouldCreateUserEntityFromUserSuccessfully() {
        UUID id = UUID.randomUUID();
        Address address = Address.create("Rua A", "Cidade B", "Estado C", "12345-678");
        LocalDateTime now = LocalDateTime.now();

        User user = User.create(id, "Nome", "email@valido.com", "login", "senha", UserType.OWNER, now, now, address);

        UserEntity entity = UserEntity.toUserEntity(user);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals("Nome", entity.getName());
        assertEquals("email@valido.com", entity.getEmail());
        assertEquals("login", entity.getLogin());
        assertEquals("senha", entity.getPassword());
        assertEquals(UserTypeEnum.OWNER, entity.getUserType());
        assertEquals(now, entity.getCreateDate());
        assertEquals(now, entity.getLastChange());
        assertNotNull(entity.getAddressEntity());
        assertEquals("Rua A", entity.getAddressEntity().getStreet());
    }

    @Test
    void shouldCreateUserEntityFromUserDTOSuccessfully() {
        UUID id = UUID.randomUUID();
        AddressDTO addressDTO = new AddressDTO("Rua X", "Cidade Y", "Estado Z", "99999-999");
        LocalDateTime now = LocalDateTime.now();

        UserDTO userDTO = new UserDTO(id, "NomeDTO", "dto@email.com", "loginDTO", "senhaDTO", UserType.CLIENT, addressDTO, now, now);

        UserEntity entity = UserEntity.toUserEntityFromDTO(userDTO);

        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals("NomeDTO", entity.getName());
        assertEquals("dto@email.com", entity.getEmail());
        assertEquals("loginDTO", entity.getLogin());
        assertEquals("senhaDTO", entity.getPassword());
        assertEquals(UserTypeEnum.CLIENT, entity.getUserType());
        assertEquals(now, entity.getCreateDate());
        assertEquals(now, entity.getLastChange());
        assertNotNull(entity.getAddressEntity());
        assertEquals("Rua X", entity.getAddressEntity().getStreet());
    }

    @Test
    void shouldConvertListOfUsersToListOfUserEntities() {
        Address address = Address.create("Rua A", "Cidade B", "Estado C", "12345-678");
        User user1 = User.create(UUID.randomUUID(), "User1", "user1@email.com", "user1",
                "senha1", UserType.CLIENT, null, null, address);
        User user2 = User.create(UUID.randomUUID(), "User2", "user2@email.com", "user2",
                "senha2", UserType.OWNER, null, null, address);

        List<UserEntity> entities = UserEntity.toUserEntity(List.of(user1, user2));

        assertEquals(2, entities.size());
        assertEquals("User1", entities.get(0).getName());
        assertEquals(UserTypeEnum.CLIENT, entities.get(0).getUserType());
        assertEquals("User2", entities.get(1).getName());
        assertEquals(UserTypeEnum.OWNER, entities.get(1).getUserType());
    }

    @Test
    void shouldConvertListOfUserDTOsToListOfUserEntities() {
        AddressDTO addressDTO = new AddressDTO("Rua A", "Cidade B", "Estado C", "12345-678");
        UserDTO userDTO1 = new UserDTO(UUID.randomUUID(), "User1", "user1@email.com", "user1",
                "senha1", UserType.CLIENT, addressDTO, null, null);
        UserDTO userDTO2 = new UserDTO(UUID.randomUUID(), "User2", "user2@email.com", "user2",
                "senha2", UserType.OWNER, addressDTO, null, null);

        List<UserEntity> entities = UserEntity.toUserEntityFromDTO(List.of(userDTO1, userDTO2));

        assertEquals(2, entities.size());
        assertEquals("User1", entities.get(0).getName());
        assertEquals(UserTypeEnum.CLIENT, entities.get(0).getUserType());
        assertEquals("User2", entities.get(1).getName());
        assertEquals(UserTypeEnum.OWNER, entities.get(1).getUserType());
    }
}