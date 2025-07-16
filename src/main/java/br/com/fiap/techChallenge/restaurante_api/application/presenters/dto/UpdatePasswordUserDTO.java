package br.com.fiap.techChallenge.restaurante_api.application.presenters.dto;

import java.util.UUID;

public record UpdatePasswordUserDTO(String email, String login, String newPassword, String oldPassword) {
}
