package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class MenuItem {
    private UUID id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private BigDecimal price;
    private boolean onlyLocal;
    private String urlFoto;
    @NotNull
    private Restaurant restaurant;

    public static MenuItem create(UUID id, String name, String description, BigDecimal price, boolean onlyLocal,
                                  String urlFoto, Restaurant restaurant) {
        return MenuItem.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .onlyLocal(onlyLocal)
                .urlFoto(urlFoto)
                .restaurant(restaurant)
                .build();
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser nulo ou negativo.");
        }
        this.price = price;
    }
}
