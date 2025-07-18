package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AddressEntity {

    private String street;
    private String city;
    private String state;
    private String zipCode;

    public static AddressEntity toAddressEntity(Address user) {
        return AddressEntity.builder()
                .street(user.getStreet())
                .city(user.getCity())
                .state(user.getState())
                .zipCode(user.getZipCode())
                .build();
    }

    public static List<AddressEntity> toAddressEntity(List<Address> addresses) {
        return addresses.stream()
                .map(AddressEntity::toAddressEntity)
                .toList();
    }

    public static AddressEntity toAddressEntityFromDTO(AddressDTO addressDTO) {
        return AddressEntity.builder()
                .street(addressDTO.street())
                .city(addressDTO.city())
                .state(addressDTO.state())
                .zipCode(addressDTO.zipCode())
                .build();
    }

    public static List<AddressEntity> toAddressEntityFromDTO(List<AddressDTO> addressesDTO) {
        return addressesDTO.stream()
                .map(AddressEntity::toAddressEntityFromDTO)
                .toList();
    }
}
