package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
