package br.com.fiap.techChallenge.restaurante_api.core.dto;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String email,
        AddressDTO addressDTO,
        String login,
        UserType userType,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createDate) {
}
