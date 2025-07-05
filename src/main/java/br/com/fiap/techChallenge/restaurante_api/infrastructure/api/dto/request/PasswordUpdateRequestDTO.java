package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request;

import lombok.Data;

@Data
public class PasswordUpdateRequestDTO {
    private String oldPassword;
    private String newPassword;
}
