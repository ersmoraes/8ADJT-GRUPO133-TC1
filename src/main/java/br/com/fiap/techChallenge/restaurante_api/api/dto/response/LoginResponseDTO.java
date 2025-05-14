package br.com.fiap.techChallenge.restaurante_api.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;
    private String type;
    private String status;
}
