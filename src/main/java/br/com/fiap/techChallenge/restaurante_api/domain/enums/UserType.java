package br.com.fiap.techChallenge.restaurante_api.domain.enums;

import java.util.Arrays;

public enum UserType {
    CLIENT,
    OWNER;

    public static UserType fromString(String type) {
        return Arrays.stream(UserType.values())
                .filter(u -> u.name().equalsIgnoreCase(type)).findAny().orElse(null);
    }
}
