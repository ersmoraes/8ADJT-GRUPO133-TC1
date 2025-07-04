package br.com.fiap.techChallenge.restaurante_api.apiOLD.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
