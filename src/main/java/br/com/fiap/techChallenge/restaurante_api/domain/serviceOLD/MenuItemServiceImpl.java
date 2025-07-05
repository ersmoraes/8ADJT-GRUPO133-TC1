package br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.MenuItemRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.MenuItemResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.ItemCardapio;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Restaurante;
import br.com.fiap.techChallenge.restaurante_api.domain.repositoryOLD.MenuItemRepository;
import br.com.fiap.techChallenge.restaurante_api.domain.repositoryOLD.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuItemResponseDTO cadastrar(MenuItemRequestDTO dto) {
        Restaurante restaurante = restaurantRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        ItemCardapio item = ItemCardapio.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .apenasNoLocal(dto.apenasNoLocal())
                .caminhoFoto(dto.caminhoFoto())
                .restaurante(restaurante)
                .build();

        menuItemRepository.save(item);

        return toDTO(item);
    }

    @Override
    public List<MenuItemResponseDTO> listarTodos() {
        return menuItemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDTO buscarPorId(UUID id) {
        ItemCardapio item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
        return toDTO(item);
    }

    @Override
    public MenuItemResponseDTO atualizar(UUID id, MenuItemRequestDTO dto) {
        ItemCardapio item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        Restaurante restaurante = restaurantRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        item.setNome(dto.nome());
        item.setDescricao(dto.descricao());
        item.setPreco(dto.preco());
        item.setApenasNoLocal(dto.apenasNoLocal());
        item.setCaminhoFoto(dto.caminhoFoto());
        item.setRestaurante(restaurante);

        menuItemRepository.save(item);

        return toDTO(item);
    }

    @Override
    public void deletar(UUID id) {
        ItemCardapio item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
        menuItemRepository.delete(item);
    }

    private MenuItemResponseDTO toDTO(ItemCardapio item) {
        return new MenuItemResponseDTO(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.isApenasNoLocal(),
                item.getCaminhoFoto(),
                item.getRestaurante().getNome()
        );
    }
}
