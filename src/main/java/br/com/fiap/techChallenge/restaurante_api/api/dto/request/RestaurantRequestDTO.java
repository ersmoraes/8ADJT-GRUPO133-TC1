package br.com.fiap.techChallenge.restaurante_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RestaurantRequestDTO(
        @NotBlank String nome,
        @NotBlank String endereco,
        @NotBlank String tipoCozinha,
        @NotBlank String horarioFuncionamento,
        @NotNull UUID donoId
) {
}
