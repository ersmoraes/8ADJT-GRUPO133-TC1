package br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "usuario", nullable = false, unique = true)
    private String login;

    @Column(name = "senha", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private UserType userType;

    @Column(name = "dt_criacao")
    public LocalDateTime createDate;

    @Column(name = "dt_alteracao")
    private LocalDateTime lastChange;

    @Embedded
    private Address address;
}
