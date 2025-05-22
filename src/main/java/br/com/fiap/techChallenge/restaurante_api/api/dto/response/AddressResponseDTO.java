package br.com.fiap.techChallenge.restaurante_api.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
}
