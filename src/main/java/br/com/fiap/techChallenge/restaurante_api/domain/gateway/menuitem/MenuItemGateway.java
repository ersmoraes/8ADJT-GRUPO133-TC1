package br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class MenuItemGateway implements IMenuItemGateway{

    private final IMenuItemDataSource menuItemDataSource;

    private MenuItemGateway(IMenuItemDataSource menuItemDataSource){
        this.menuItemDataSource = menuItemDataSource;
    }

    public static MenuItemGateway create(IMenuItemDataSource menuItemDataSource){
        return new MenuItemGateway(menuItemDataSource);
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO(null, menuItem.getName(), menuItem.getDescription(), menuItem.getPrice(), menuItem.isOnlyLocal(),menuItem.getUrlFoto());
        MenuItemDTO menuItemDTO = menuItemDataSource.create(dto);
        return MenuItem.create(menuItemDTO.id(), menuItemDTO.name(), menuItemDTO.description(), menuItemDTO.price(), menuItemDTO.onlyLocal(), menuItemDTO.urlFoto());
    }

    @Override
    public MenuItem findById(UUID id) {
        MenuItemDTO dto = menuItemDataSource.findById(id);
        MenuItem menuItem = MenuItem.create(dto.id(), dto.name(), dto.description(), dto.price(), dto.onlyLocal(), dto.urlFoto());
        return menuItem;
    }

    @Override
    public MenuItem findByName(String name) {
        MenuItemDTO byName = menuItemDataSource.findByName(name);
        MenuItem menuItem = MenuItem.create(byName.id(), byName.name(), byName.description(), byName.price(), byName.onlyLocal(), byName.urlFoto());
        return menuItem;
    }

    @Override
    public boolean existsByName(String name) {
        return menuItemDataSource.existsByName(name);
    }

    @Override
    public Page<MenuItem> findAll(Pageable pageable) {
        Page<MenuItemDTO> allDto = menuItemDataSource.findAll(pageable);
        Page<MenuItem> menuItemPage = allDto.map(dto ->  MenuItem.create(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.onlyLocal(),
                dto.urlFoto()
        ));
        return menuItemPage;
    }

    @Override
    public void deleteMenuItem(UUID id) {
        this.menuItemDataSource.deleteMenuItem(id);
    }

    @Override
    @Transactional
    public void update(MenuItem menuItem) {
        menuItemDataSource.update(menuItem);
    }
}
