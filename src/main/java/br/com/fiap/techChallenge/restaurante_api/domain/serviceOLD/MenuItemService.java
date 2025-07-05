package br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.MenuItemRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MenuItemService {

    MenuItemResponseDTO cadastrar(MenuItemRequestDTO dto);

    List<MenuItemResponseDTO> listarTodos();
    MenuItemResponseDTO buscarPorId(UUID id);

    MenuItemResponseDTO atualizar(UUID id, MenuItemRequestDTO dto);

    void deletar(UUID id);
}