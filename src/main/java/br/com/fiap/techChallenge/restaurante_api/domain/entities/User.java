package br.com.fiap.techChallenge.restaurante_api.domain.entities;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotEmpty
    private UUID id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String login;
    private String password;
    @NotNull
    private UserType userType;
    private LocalDateTime createDate;
    private LocalDateTime lastChange;
    private Address address;

    public static User create(UUID id, String name, String email, String login, String password, UserType userType,
                              LocalDateTime createDate, LocalDateTime lastChange, Address address) {

        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .login(login)
                .password(password)
                .userType(userType)
                .createDate(createDate)
                .lastChange(lastChange)
                .address(address)
                .build();
    }

    public void setEmail(String email) {
        validEmail(email);
        this.email = email;
    }

    private static void validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public static User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .userType(UserType.fromString(userEntity.getUserType().name()))
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

    public static User toUserFromDTO(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.id())
                .name(userDTO.name())
                .email(userDTO.email())
                .login(userDTO.login())
                .password(userDTO.password())
                .userType(UserType.fromString(userDTO.userType().name()))
                .createDate(userDTO.createDate())
                .lastChange(userDTO.lastChange())
                .address(Address.toAddressFromDTO(userDTO.addressDTO()))
                .build();
    }

    public static List<User> toUserFromDTO(List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .map(User::toUserFromDTO)
                .toList();
    }
}
