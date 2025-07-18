package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.MenuItemRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service.MenuItemServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itens-cardapio")
public class MenuItemController {

    private final MenuItemServiceImpl menuItemService;

    public MenuItemController(MenuItemServiceImpl menuItemService) {
        this.menuItemService = menuItemService;
    }

//    @PostMapping
//    public ResponseEntity<MenuItemResponseDTO> cadastrar(@RequestBody @Valid MenuItemRequestDTO dto) {
//        MenuItemResponseDTO criado = menuItemService.cadastrar(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<MenuItemResponseDTO>> listarTodos() {
//        return ResponseEntity.ok(menuItemService.listarTodos());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MenuItemResponseDTO> buscarPorId(@PathVariable UUID id) {
//        return ResponseEntity.ok(menuItemService.buscarPorId(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<MenuItemResponseDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid MenuItemRequestDTO dto) {
//        return ResponseEntity.ok(menuItemService.atualizar(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
//        menuItemService.deletar(id);
//        return ResponseEntity.noContent().build();
//    }
}
