package br.com.fiap.techChallenge.restaurante_api.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_usuario")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeTipo;
}

