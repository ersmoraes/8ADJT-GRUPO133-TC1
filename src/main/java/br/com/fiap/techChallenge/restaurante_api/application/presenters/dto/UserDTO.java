package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record UserDTO(
        UUID id,
        String name,
        String email,
        String login,
        String password,
        UserType userType,
        AddressDTO addressDTO,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createDate,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime lastChange) {
    public User parser() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .login(login)
                .password(password)
                .userType(userType)
                .address(addressDTO.parser())
                .createDate(createDate)
                .lastChange(lastChange)
                .build();
    }

    public static UserDTO toUserDTOFromEntity(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .userType(UserType.fromString(userEntity.getUserType().name()))
                .createDate(userEntity.getCreateDate())
                .lastChange(userEntity.getLastChange())
                .addressDTO(AddressDTO.toAddressDTOFromAddressEntity(userEntity.getAddressEntity()))
                .build();
    }

    public static List<UserDTO> toUserDTOFromEntity(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserDTO::toUserDTOFromEntity)
                .toList();
    }

    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .userType(UserType.fromString(user.getUserType().name()))
                .createDate(user.getCreateDate())
                .lastChange(user.getLastChange())
                .addressDTO(AddressDTO.toAddressDTO(user.getAddress()))
                .build();
    }

    public static List<UserDTO> toUserDTO(List<User> userEntities) {
        return userEntities.stream()
                .map(UserDTO::toUserDTO)
                .toList();
    }
}
