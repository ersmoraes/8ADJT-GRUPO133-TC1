package br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model;

import jakarta.persistence.Embeddable;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;
    private String zipCode;
}
