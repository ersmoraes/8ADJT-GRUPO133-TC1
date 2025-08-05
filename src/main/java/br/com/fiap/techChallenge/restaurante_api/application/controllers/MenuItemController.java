package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem.CreateMenuItemUseCase;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.MenuItemGateway;

public class MenuItemController {

    IMenuItemDataSource dataSource;
    IMenuItemGateway gateway;

    private MenuItemController(IMenuItemDataSource dataSource) {
        this.dataSource = dataSource;
        this.gateway = MenuItemGateway.create(dataSource);
    }

    public static MenuItemController create(IMenuItemDataSource dataSource) {
        return new MenuItemController(dataSource);
    }

    public MenuItem create(MenuItemDTO menuItemDTO) {
        CreateMenuItemUseCase createMenuItemUseCase = CreateMenuItemUseCase.create(gateway);
        return createMenuItemUseCase.execute(menuItemDTO);
    }
}
