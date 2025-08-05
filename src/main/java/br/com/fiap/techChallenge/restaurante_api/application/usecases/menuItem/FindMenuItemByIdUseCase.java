package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;

import java.util.UUID;

public class FindMenuItemByIdUseCase {

    IMenuItemGateway gateway;

    private FindMenuItemByIdUseCase(IMenuItemGateway gateway){
        this.gateway = gateway;
    }

    public static FindMenuItemByIdUseCase create(IMenuItemGateway gateway){
        return new FindMenuItemByIdUseCase(gateway);
    }

    public MenuItem execute(UUID id){
        return this.gateway.findById(id);
    }

}
