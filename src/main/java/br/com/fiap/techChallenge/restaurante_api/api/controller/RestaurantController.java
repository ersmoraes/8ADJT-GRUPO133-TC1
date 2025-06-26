package br.com.fiap.techChallenge.restaurante_api.api.controller;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.RestaurantRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.RestaurantResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> cadastrar(@RequestBody @Valid RestaurantRequestDTO dto) {
        return ResponseEntity.ok(restaurantService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> listar() {
        return ResponseEntity.ok(restaurantService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(restaurantService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid RestaurantRequestDTO dto) {
        return ResponseEntity.ok(restaurantService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        restaurantService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}