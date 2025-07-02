package br.com.fiap.techChallenge.restaurante_api.core.entities;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Endereco;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Address {
    private String street;
    private String city;
    private String state;
    private String cep;

    public static Address create(String street, String city, String state, String cep) throws IllegalArgumentException {
        if (street == null || street.isEmpty() || city == null || city.isEmpty() || state == null || state.isEmpty()
                || cep == null || cep.isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório não preenchido: street, city, state ou cep");
        }

        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .cep(cep)
                .build();
    }
}
