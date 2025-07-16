package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

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
    private UserTypeEnum userType;

    @Column(name = "dt_criacao")
    public LocalDateTime createDate;

    @Column(name = "dt_alteracao")
    private LocalDateTime lastChange;

    @Embedded
    private AddressEntity addressEntity;

    public static UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .userType(UserTypeEnum.fromString(user.getUserType().name()))
                .createDate(user.getCreateDate())
                .lastChange(user.getLastChange())
                .addressEntity(AddressEntity.toAddressEntity(user.getAddress()))
                .build();
    }

    public static List<UserEntity> toUserEntity(List<User> users) {
        return users.stream()
                .map(UserEntity::toUserEntity)
                .toList();
    }
    public static UserEntity toUserEntityFromDTO(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.id())
                .name(userDTO.name())
                .email(userDTO.email())
                .login(userDTO.login())
                .password(userDTO.password())
                .userType(UserTypeEnum.fromString(userDTO.userType().name()))
                .createDate(userDTO.createDate())
                .lastChange(userDTO.lastChange())
                .addressEntity(AddressEntity.toAddressEntityFromDTO(userDTO.addressDTO()))
                .build();
    }

    public static List<UserEntity> toUserEntityFromDTO(List<UserDTO> usersDTO) {
        return usersDTO.stream()
                .map(UserEntity::toUserEntityFromDTO)
                .toList();
    }
}
