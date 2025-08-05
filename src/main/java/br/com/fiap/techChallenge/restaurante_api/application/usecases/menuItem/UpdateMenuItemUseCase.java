package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;

import java.util.UUID;

public class UpdateMenuItemUseCase {

    IMenuItemGateway menuItemGateway;

    private UpdateMenuItemUseCase(IMenuItemGateway menuItemGateway){
        this.menuItemGateway = menuItemGateway;
    }

    public static UpdateMenuItemUseCase create(IMenuItemGateway menuItemGateway) {
        return new UpdateMenuItemUseCase(menuItemGateway);
    }

    public void execute(MenuItem menuItem) {
        menuItemGateway.update(menuItem);
    }

}
