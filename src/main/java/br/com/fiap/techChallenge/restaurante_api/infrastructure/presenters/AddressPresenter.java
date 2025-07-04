package br.com.fiap.techChallenge.restaurante_api.infrastructure.presenters;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;

public class AddressPresenter {

    public static AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
