package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserGateway;

import java.util.UUID;

public class DeleteMenuItemUseCase {

    IMenuItemGateway menuItemGateway;

    private DeleteMenuItemUseCase(IMenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public static DeleteMenuItemUseCase create(IMenuItemGateway menuItemGateway) {
        return new DeleteMenuItemUseCase(menuItemGateway);
    }

    public void execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        this.menuItemGateway.deleteMenuItem(id);
    }
}
