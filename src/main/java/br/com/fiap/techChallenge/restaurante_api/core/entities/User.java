package br.com.fiap.techChallenge.restaurante_api.core.entities;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Endereco;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    public LocalDateTime createDate;
    private LocalDateTime lastChange;
    private Endereco address;

    public static User create(String name, String email, String login, String password, UserType userType,
                              Endereco address) throws IllegalArgumentException {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || login == null || login.isEmpty()
                || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório não preenchido: name, email, login ou password");
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
}
