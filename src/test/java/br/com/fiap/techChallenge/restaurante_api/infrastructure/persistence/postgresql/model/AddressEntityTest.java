package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressEntityTest {

    @Test
    void shouldCreateAddressEntityFromAddressSuccessfully() {
        Address address = Address.create("Rua A", "Cidade B", "Estado C", "12345-678");

        AddressEntity entity = AddressEntity.toAddressEntity(address);

        assertNotNull(entity);
        assertEquals("Rua A", entity.getStreet());
        assertEquals("Cidade B", entity.getCity());
        assertEquals("Estado C", entity.getState());
        assertEquals("12345-678", entity.getZipCode());
    }

    @Test
    void shouldCreateAddressEntityFromAddressDTOSuccessfully() {
        AddressDTO dto = new AddressDTO("Rua X", "Cidade Y", "Estado Z", "99999-999");

        AddressEntity entity = AddressEntity.toAddressEntityFromDTO(dto);

        assertNotNull(entity);
        assertEquals("Rua X", entity.getStreet());
        assertEquals("Cidade Y", entity.getCity());
        assertEquals("Estado Z", entity.getState());
        assertEquals("99999-999", entity.getZipCode());
    }

    @Test
    void shouldConvertListOfAddressesToListOfAddressEntities() {
        Address address1 = Address.create("Rua 1", "Cidade 1", "Estado 1", "11111-111");
        Address address2 = Address.create("Rua 2", "Cidade 2", "Estado 2", "22222-222");

        List<AddressEntity> entities = AddressEntity.toAddressEntity(List.of(address1, address2));

        assertEquals(2, entities.size());
        assertEquals("Rua 1", entities.get(0).getStreet());
        assertEquals("Rua 2", entities.get(1).getStreet());
    }

    @Test
    void shouldConvertListOfAddressDTOsToListOfAddressEntities() {
        AddressDTO dto1 = new AddressDTO("Rua 1", "Cidade 1", "Estado 1", "11111-111");
        AddressDTO dto2 = new AddressDTO("Rua 2", "Cidade 2", "Estado 2", "22222-222");

        List<AddressEntity> entities = AddressEntity.toAddressEntityFromDTO(List.of(dto1, dto2));

        assertEquals(2, entities.size());
        assertEquals("Rua 1", entities.get(0).getStreet());
        assertEquals("Rua 2", entities.get(1).getStreet());
    }
}