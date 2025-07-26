package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "item_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    @Column(name = "apenas_local")
    private boolean onlyLocal;

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private RestaurantEntity restaurant;

}

