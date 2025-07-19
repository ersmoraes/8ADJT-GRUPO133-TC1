package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request;

import lombok.Data;

@Data
public class AddressRequestDTO {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
