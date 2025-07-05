package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemRequestDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoLocal,
        String caminhoFoto,
        UUID restauranteId
) {
}
