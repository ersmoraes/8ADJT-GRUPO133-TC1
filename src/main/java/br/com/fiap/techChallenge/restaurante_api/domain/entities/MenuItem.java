package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Restaurante;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class MenuItem {
    private String name;
    private String description;
    private BigDecimal price;
    private boolean noDelivery;
    private String imageUrl;
    private Restaurante restaurant;

    public static MenuItem create(String name, String description, BigDecimal price, boolean noDelivery,
                                  String imageUrl, Restaurante restaurant) {
        if (name == null || name.isEmpty() || description == null || description.isEmpty() || price == null
                || restaurant == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos: nome, descrição, " +
                    "preço ou restaurante");
        }

        return MenuItem.builder()
                .name(name)
                .description(description)
                .price(price)
                .noDelivery(noDelivery)
                .imageUrl(imageUrl)
                .restaurant(restaurant)
                .build();
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        this.price = price;
    }
}
