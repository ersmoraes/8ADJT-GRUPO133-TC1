package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class User {
    private UUID id;
    private String name;
    private String email;
    private String login;
    private String password;
    private UserType userType;
    private LocalDateTime createDate;
    private LocalDateTime lastChange;
    private Address address;

    public static User create(String name, String email, String login, String password, UserType userType,
                              Address address) throws IllegalArgumentException {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || login == null || login.isEmpty()
                || userType == null || address == null) {
            throw new IllegalArgumentException("Campo obrigatório não preenchido: name, email, login, tipo de " +
                    "usuário ou endereço");
        }

        return User.builder()
                .name(name)
                .email(email)
                .login(login)
                .password(password)
                .userType(userType)
                .address(address)
                .build();
    }

    private static void validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public void setEmail(String email) {
        validEmail(email);
        this.email = email;
    }

    public static User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .userType(userEntity.getUserType())
                .createDate(userEntity.getCreateDate())
                .lastChange(userEntity.getLastChange())
                .address(Address.toAddress(userEntity.getAddressEntity()))
                .build();
    }

    public static List<User> toUser(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(User::toUser)
                .toList();
    }
}
