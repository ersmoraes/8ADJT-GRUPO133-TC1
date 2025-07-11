package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums;

import java.util.Arrays;

public enum UserTypeEnum {
    CLIENTE,
    PROPRIETARIO;

    public static UserTypeEnum fromString(String type) {
        return Arrays.stream(UserTypeEnum.values())
                .filter(u -> u.name().equalsIgnoreCase(type)).findAny().orElse(null);
    }
}
