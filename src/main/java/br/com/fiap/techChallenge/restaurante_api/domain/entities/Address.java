package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

    public static Address create(String street, String city, String state, String zipCode) throws IllegalArgumentException {
        if (street == null || street.isEmpty() || city == null || city.isEmpty() || state == null || state.isEmpty()
                || zipCode == null || zipCode.isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório não preenchido: street, city, state ou zipCode");
        }

        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .build();
    }

    public static Address toAddress(AddressEntity address) {
        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static List<Address> toAddress(List<AddressEntity> addressEntities) {
        return addressEntities.stream()
                .map(Address::toAddress)
                .toList();
    }

    public static Address toAddressFromDTO(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.street())
                .city(addressDTO.city())
                .state(addressDTO.state())
                .zipCode(addressDTO.zipCode())
                .build();
    }

    public static List<Address> toAddressFromDTO(List<AddressDTO> addressDTOS) {
        return addressDTOS.stream()
                .map(Address::toAddressFromDTO)
                .toList();
    }
}
