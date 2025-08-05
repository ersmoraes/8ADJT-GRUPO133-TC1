package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoLocal,
        String caminhoFoto
) {
}
