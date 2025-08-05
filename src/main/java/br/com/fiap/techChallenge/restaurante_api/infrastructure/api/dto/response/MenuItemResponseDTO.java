package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;

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

    public static MenuItemResponseDTO fromDomain(MenuItem item) {
        return new MenuItemResponseDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.isOnlyLocal(), item.getUrlFoto());
    }
}
