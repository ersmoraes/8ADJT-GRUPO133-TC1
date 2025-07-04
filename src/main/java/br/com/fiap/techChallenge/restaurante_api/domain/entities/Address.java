package br.com.fiap.techChallenge.restaurante_api.domain.entities;

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
}
