package br.com.fiap.techChallenge.restaurante_api.domain.modelOLD;

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
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(name = "tipo_cozinha", nullable = false)
    private String tipoCozinha;

    @Column(name = "horario_funcionamento", nullable = false)
    private String horarioFuncionamento;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "dono_id", nullable = false)
//    private Usuario dono;
}
