package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FindAllMenuItemUseCase {

    IMenuItemGateway menuItemGateway;

    private FindAllMenuItemUseCase(IMenuItemGateway menuItemGateway){
        this.menuItemGateway = menuItemGateway;
    }

    public static FindAllMenuItemUseCase create(IMenuItemGateway menuItemGateway){
        return new FindAllMenuItemUseCase(menuItemGateway);
    }

    public Page<MenuItem> execute(Pageable pageable){
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable não pode ser nulo");
        }

        return this.menuItemGateway.findAll(pageable);
    }
}
