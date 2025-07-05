package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import java.util.UUID;

public record RestaurantResponseDTO(
        UUID id,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento
//        ,
//        UUID donoId,
//        String nomeDono
) {
}
