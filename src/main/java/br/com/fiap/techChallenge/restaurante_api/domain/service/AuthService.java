package br.com.fiap.techChallenge.restaurante_api.domain.service;

public interface AuthService {
    String authenticate(String login, String password);
}
