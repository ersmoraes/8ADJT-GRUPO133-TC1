package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem.CreateMenuItemUseCase;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem.DeleteMenuItemUseCase;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem.FindAllMenuItemUseCase;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.user.DeleteUserUseCase;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.MenuItemGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

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

    public Page<MenuItem> findAll(Pageable pageable){
        FindAllMenuItemUseCase findAllMenuItemUseCase = FindAllMenuItemUseCase.create(gateway);
        return findAllMenuItemUseCase.execute(pageable);
    }

    public void delete(UUID id) throws IllegalArgumentException {
        var useCase = DeleteMenuItemUseCase.create(gateway);
        try {
            useCase.execute(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao deletar Item do Menu: " + e.getMessage(), e);
        }
    }
}
