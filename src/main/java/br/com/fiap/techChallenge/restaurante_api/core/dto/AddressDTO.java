package br.com.fiap.techChallenge.restaurante_api.core.dto;


import br.com.fiap.techChallenge.restaurante_api.core.entities.Address;

public record AddressDTO(String street, String city, String state, String zipCode) {
    public Address parser() {
        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .build();
    }
}
