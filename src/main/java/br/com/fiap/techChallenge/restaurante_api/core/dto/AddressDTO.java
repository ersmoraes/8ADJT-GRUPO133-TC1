package br.com.fiap.techChallenge.restaurante_api.core.dto;


import br.com.fiap.techChallenge.restaurante_api.domain.model.Endereco;

public record AddressDTO(String street, String city, String state, String cep) {
    public Endereco parser() {
        return Endereco.builder()
                .logradouro(street)
                .cidade(city)
                .estado(state)
                .cep(cep)
                .build();
    }
}
