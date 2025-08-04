package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldCreateAddressSuccessfullyWhenAllFieldsAreValid() {
        Address address = Address.create("Rua A", "Cidade B", "Estado C", "12345-678");

        assertNotNull(address);
        assertEquals("Rua A", address.getStreet());
        assertEquals("Cidade B", address.getCity());
        assertEquals("Estado C", address.getState());
        assertEquals("12345-678", address.getZipCode());
    }

    @Test
    void shouldThrowExceptionWhenStreetIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Address.create(null, "Cidade", "Estado", "12345-678");
        });
        assertEquals("Campo obrigatório não preenchido: street, city, state ou zipCode", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCityIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Address.create("Rua", "", "Estado", "12345-678");
        });
        assertEquals("Campo obrigatório não preenchido: street, city, state ou zipCode", exception.getMessage());
    }

    @Test
    void shouldConvertAddressEntityToAddressSuccessfully() {
        AddressEntity entity = new AddressEntity();
        entity.setStreet("Rua X");
        entity.setCity("Cidade Y");
        entity.setState("Estado Z");
        entity.setZipCode("00000-000");

        Address address = Address.toAddress(entity);

        assertNotNull(address);
        assertEquals("Rua X", address.getStreet());
        assertEquals("Cidade Y", address.getCity());
        assertEquals("Estado Z", address.getState());
        assertEquals("00000-000", address.getZipCode());
    }

    @Test
    void shouldReturnNullWhenAddressEntityIsNull() {
        Address address = Address.toAddress((AddressEntity) null);
        assertNull(address);
    }

    @Test
    void shouldConvertAddressDTOToAddressSuccessfully() {
        AddressDTO dto = new AddressDTO("Rua D", "Cidade E", "Estado F", "11111-111");

        Address address = Address.toAddressFromDTO(dto);

        assertNotNull(address);
        assertEquals("Rua D", address.getStreet());
        assertEquals("Cidade E", address.getCity());
        assertEquals("Estado F", address.getState());
        assertEquals("11111-111", address.getZipCode());
    }

    @Test
    void shouldReturnNullWhenAddressDTOIsNull() {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        addressDTOS.add(null);
        List<Address> address = Address.toAddressFromDTO(addressDTOS);
        assertNull(address.get(0));
    }

    @Test
    void shouldConvertListOfAddressEntitiesToListOfAddresses() {
        AddressEntity entity1 = new AddressEntity();
        entity1.setStreet("Rua 1");
        entity1.setCity("Cidade 1");
        entity1.setState("Estado 1");
        entity1.setZipCode("11111-111");

        AddressEntity entity2 = new AddressEntity();
        entity2.setStreet("Rua 2");
        entity2.setCity("Cidade 2");
        entity2.setState("Estado 2");
        entity2.setZipCode("22222-222");

        List<Address> addresses = Address.toAddress(List.of(entity1, entity2));

        assertEquals(2, addresses.size());
        assertEquals("Rua 1", addresses.get(0).getStreet());
        assertEquals("Rua 2", addresses.get(1).getStreet());
    }

    @Test
    void shouldConvertListOfAddressDTOsToListOfAddresses() {
        AddressDTO dto1 = new AddressDTO("Rua 1", "Cidade 1", "Estado 1", "11111-111");
        AddressDTO dto2 = new AddressDTO("Rua 2", "Cidade 2", "Estado 2", "22222-222");

        List<Address> addresses = Address.toAddressFromDTO(List.of(dto1, dto2));

        assertEquals(2, addresses.size());
        assertEquals("Rua 1", addresses.get(0).getStreet());
        assertEquals("Rua 2", addresses.get(1).getStreet());
    }
}