package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.RestaurantRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.RestaurantResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements IRestaurantDataSource {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDTO create(RestaurantDTO dto) {
        return null;
    }

    @Override
    public Page<RestaurantDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public RestaurantDTO findById(UUID id) {
        return null;
    }

    @Override
    public RestaurantDTO findByName(String name) {
        return null;
    }

//    @Override
//    public RestaurantResponseDTO cadastrar(RestaurantRequestDTO dto) {
////        Usuario dono = usuarioRepository.findById(dto.donoId())
////                .orElseThrow(() -> new ResourceNotFoundException("Usuário (dono) não encontrado"));
//
//        RestaurantEntity restaurante = RestaurantEntity.builder()
//                .nome(dto.nome())
//                .endereco(dto.endereco())
//                .kitchenType(dto.tipoCozinha())
//                .openingHours(dto.horarioFuncionamento())
////                .dono(dono)
//                .build();
//
//        restaurantRepository.save(restaurante);
//
//        return toDTO(restaurante);
//    }
//
//    @Override
//    public List<RestaurantResponseDTO> listarTodos() {
//        return restaurantRepository.findAll().stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public RestaurantResponseDTO buscarPorId(UUID id) {
//        RestaurantEntity restaurante = restaurantRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
//        return toDTO(restaurante);
//    }
//
//    @Override
//    public RestaurantResponseDTO atualizar(UUID id, RestaurantRequestDTO dto) {
//        RestaurantEntity restaurante = restaurantRepository.findById(dto.donoId())
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
//
////        Usuario dono = usuarioRepository.findById(dto.donoId())
////                .orElseThrow(() -> new ResourceNotFoundException("Usuário (dono) não encontrado"));
//
//        restaurante.setNome(dto.nome());
//        restaurante.setEndereco(dto.endereco());
//        restaurante.setKitchenType(dto.tipoCozinha());
//        restaurante.setOpeningHours(dto.horarioFuncionamento());
////        restaurante.setDono(dono);
//
//        restaurantRepository.save(restaurante);
//
//        return toDTO(restaurante);
//    }
//
//    @Override
//    public void deletar(UUID id) {
//        RestaurantEntity restaurante = restaurantRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
//        restaurantRepository.delete(restaurante);
//    }
//
//    private RestaurantResponseDTO toDTO(RestaurantEntity restaurante) {
//        return new RestaurantResponseDTO(
//                restaurante.getId(),
//                restaurante.getNome(),
//                restaurante.getEndereco(),
//                restaurante.getKitchenType(),
//                restaurante.getOpeningHours()
////                ,
////                restaurante.getDono().getId(),
////                restaurante.getDono().getName()
//        );
//    }
}
