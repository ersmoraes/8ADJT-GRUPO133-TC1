package br.com.fiap.techChallenge.restaurante_api.domain.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Proprietario extends Usuario {

    public Proprietario(){
        super();
    }


}
