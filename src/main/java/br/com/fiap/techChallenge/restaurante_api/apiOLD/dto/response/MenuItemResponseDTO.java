package br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoLocal,
        String caminhoFoto,
        String restauranteNome
) {
}
