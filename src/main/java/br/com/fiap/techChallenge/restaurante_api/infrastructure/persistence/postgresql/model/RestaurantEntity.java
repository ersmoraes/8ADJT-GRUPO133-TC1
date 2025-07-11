package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(nullable = false)
    private AddressEntity endereco;

    @Column(name = "tipo_cozinha", nullable = false)
    private String kitchenType;

    @Column(name = "horario_funcionamento", nullable = false)
    private String openingHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;
}
