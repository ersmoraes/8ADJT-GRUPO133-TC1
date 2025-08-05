package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.controllers.MenuItemController;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.MenuItemPresenter;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.MenuItemRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.MenuItemRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itens-cardapio")
public class MenuItemRESTController {

    private final MenuItemController menuItemController;

    public MenuItemRESTController(MenuItemRepository menuItemService) {
        this.menuItemController = MenuItemController.create(menuItemService);
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> create(@RequestBody @Valid MenuItemRequestDTO dto) {
        MenuItem createMenu = menuItemController.create(new MenuItemDTO(null, dto.name(), dto.description(), dto.price(), dto.onlyLocal(), dto.imgUrl()));

        MenuItemResponseDTO response = new MenuItemResponseDTO(createMenu.getId(),
                createMenu.getName(), createMenu.getDescription(), createMenu.getPrice(), createMenu.isOnlyLocal(), createMenu.getUrlFoto());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<Page<MenuItemResponseDTO>> findAll(Pageable pageable) {
        Page<MenuItem> menuItemPage = menuItemController.findAll(pageable);
        Page<MenuItemResponseDTO> response = menuItemPage.map(MenuItemResponseDTO::fromDomain);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> findById(@PathVariable UUID id){
        MenuItem item = menuItemController.findById(id);
        MenuItemResponseDTO dto = new MenuItemResponseDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.isOnlyLocal(), item.getUrlFoto());
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        menuItemController.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody MenuItemRequestDTO dto) {
        MenuItem menuItem = MenuItem.create(id, dto.name(), dto.description(), dto.price(), dto.onlyLocal(), dto.imgUrl());
        menuItemController.execute(menuItem);
        return ResponseEntity.noContent().build();
    }
}
