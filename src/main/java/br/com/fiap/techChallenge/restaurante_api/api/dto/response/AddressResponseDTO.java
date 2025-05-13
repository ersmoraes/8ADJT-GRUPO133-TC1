package br.com.fiap.techChallenge.restaurante_api.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
