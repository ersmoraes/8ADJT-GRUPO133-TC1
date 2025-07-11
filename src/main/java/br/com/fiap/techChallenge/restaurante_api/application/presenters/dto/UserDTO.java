package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

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

        public static UserDTO toUserDTO(UserEntity userEntity) {
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

        public static List<UserDTO> toUserDTO(List<UserEntity> userEntities) {
                return userEntities.stream()
                        .map(UserDTO::toUserDTO)
                        .toList();
        }
}
