package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;


import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record AddressDTO(String street, String city, String state, String zipCode) {
    public Address parser() {
        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .build();
    }

    public static AddressDTO toAddressDTOFromAddressEntity(AddressEntity address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static List<AddressDTO> toAddressDTOFromAddressEntity(List<AddressEntity> addressEntities) {
        return addressEntities.stream()
                .map(AddressDTO::toAddressDTOFromAddressEntity)
                .toList();
    }

    public static AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static List<AddressDTO> toAddressDTO(List<Address> addressEntities) {
        return addressEntities.stream()
                .map(AddressDTO::toAddressDTO)
                .toList();
    }
}
