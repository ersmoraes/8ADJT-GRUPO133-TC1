package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request;

import java.math.BigDecimal;

public record MenuItemRequestDTO(
        String name,
        String description,
        BigDecimal price,
        boolean onlyLocal,
        String imgUrl
) {
}
