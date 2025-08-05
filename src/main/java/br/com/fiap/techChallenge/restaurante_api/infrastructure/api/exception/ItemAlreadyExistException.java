package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception;

public class ItemAlreadyExistException extends RuntimeException{

    public ItemAlreadyExistException(String msg){
        super(msg);
    }
}
