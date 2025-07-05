package br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.RestaurantRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.RestaurantResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {

    RestaurantResponseDTO cadastrar(RestaurantRequestDTO dto);

    List<RestaurantResponseDTO> listarTodos();

    RestaurantResponseDTO buscarPorId(UUID id);

    RestaurantResponseDTO atualizar(UUID id, RestaurantRequestDTO dto);

    void deletar(UUID id);
}
