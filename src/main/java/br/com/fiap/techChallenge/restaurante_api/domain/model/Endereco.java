package br.com.fiap.techChallenge.restaurante_api.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Endereco {

    private String logradouro;
    private String cidade;
    private String estado;
    private String cep;
}
