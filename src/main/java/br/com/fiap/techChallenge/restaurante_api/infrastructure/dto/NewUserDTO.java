package br.com.fiap.techChallenge.restaurante_api.infrastructure.dto;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;

public record NewUserDTO(String name, String email, String login, String password, UserType userType,
                         AddressDTO address) {
}
