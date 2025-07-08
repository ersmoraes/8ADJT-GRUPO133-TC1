package br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.RestaurantRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.RestaurantResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Restaurante;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
//    private final UserRepository usuarioRepository;

    @Override
    public RestaurantResponseDTO cadastrar(RestaurantRequestDTO dto) {
//        Usuario dono = usuarioRepository.findById(dto.donoId())
//                .orElseThrow(() -> new ResourceNotFoundException("Usuário (dono) não encontrado"));

        Restaurante restaurante = Restaurante.builder()
                .nome(dto.nome())
                .endereco(dto.endereco())
                .tipoCozinha(dto.tipoCozinha())
                .horarioFuncionamento(dto.horarioFuncionamento())
//                .dono(dono)
                .build();

        restaurantRepository.save(restaurante);

        return toDTO(restaurante);
    }

    @Override
    public List<RestaurantResponseDTO> listarTodos() {
        return restaurantRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponseDTO buscarPorId(UUID id) {
        Restaurante restaurante = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
        return toDTO(restaurante);
    }

    @Override
    public RestaurantResponseDTO atualizar(UUID id, RestaurantRequestDTO dto) {
        Restaurante restaurante = restaurantRepository.findById(dto.donoId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

//        Usuario dono = usuarioRepository.findById(dto.donoId())
//                .orElseThrow(() -> new ResourceNotFoundException("Usuário (dono) não encontrado"));

        restaurante.setNome(dto.nome());
        restaurante.setEndereco(dto.endereco());
        restaurante.setTipoCozinha(dto.tipoCozinha());
        restaurante.setHorarioFuncionamento(dto.horarioFuncionamento());
//        restaurante.setDono(dono);

        restaurantRepository.save(restaurante);

        return toDTO(restaurante);
    }

    @Override
    public void deletar(UUID id) {
        Restaurante restaurante = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
        restaurantRepository.delete(restaurante);
    }

    private RestaurantResponseDTO toDTO(Restaurante restaurante) {
        return new RestaurantResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento()
//                ,
//                restaurante.getDono().getId(),
//                restaurante.getDono().getName()
        );
    }
}
