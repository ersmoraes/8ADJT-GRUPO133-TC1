package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
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
    private String name;
    private String description;
    private BigDecimal price;
    private boolean onlyLocal;
    private String urlFoto;
    private Restaurant restaurant;

    public static MenuItem create(UUID id, String name, String description, BigDecimal price, boolean onlyLocal,
                                  String urlFoto, Restaurant restaurant) {
        if (name == null || name.isEmpty() || description == null || description.isEmpty() || price == null
                || restaurant == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos: name, descrição, " +
                    "preço ou restaurante");
        }

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
