package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemDTO(UUID id,
                          String name,
                          String description,
                          BigDecimal price,
                          boolean onlyLocal,
                          String urlFoto
) {
    public MenuItemDTO withId(UUID id) {
        return new MenuItemDTO(id, this.name(), this.description(), this.price(), this.onlyLocal(), this.urlFoto());
    }
}
