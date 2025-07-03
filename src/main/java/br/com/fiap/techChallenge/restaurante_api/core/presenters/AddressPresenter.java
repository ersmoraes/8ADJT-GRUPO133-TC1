package br.com.fiap.techChallenge.restaurante_api.core.presenters;

import br.com.fiap.techChallenge.restaurante_api.core.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.core.entities.Address;

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
