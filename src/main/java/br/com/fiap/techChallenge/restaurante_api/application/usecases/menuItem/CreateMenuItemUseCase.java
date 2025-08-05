package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ItemAlreadyExistException;

public class CreateMenuItemUseCase {

    IMenuItemGateway menuItemGateway;

    private CreateMenuItemUseCase(IMenuItemGateway menuItemGateway){
        this.menuItemGateway = menuItemGateway;
    }

    public static CreateMenuItemUseCase create(IMenuItemGateway menuItemGateway){
        return new CreateMenuItemUseCase(menuItemGateway);
    }

    public MenuItem execute(MenuItemDTO menuItemDTO){
        if(menuItemDTO.name() == null || menuItemDTO.description() == null || menuItemDTO.price() == null || menuItemDTO.urlFoto() == null){
            throw new IllegalArgumentException("Existem dados a ser preenchidos!");
        }

        if(menuItemGateway.existsByName(menuItemDTO.name())){
            throw new ItemAlreadyExistException("O Item já foi cadastrado!");
        }

        final MenuItem menuItem = MenuItem.create(null, menuItemDTO.name(), menuItemDTO.description(),
                menuItemDTO.price(), menuItemDTO.onlyLocal(), menuItemDTO.urlFoto());

        return this.menuItemGateway.createMenuItem(menuItem);
    }
}
