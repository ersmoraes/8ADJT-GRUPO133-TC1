package br.com.fiap.techChallenge.restaurante_api.api.dto.request;

import lombok.Data;

@Data
public class AddressRequestDTO {
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
}
