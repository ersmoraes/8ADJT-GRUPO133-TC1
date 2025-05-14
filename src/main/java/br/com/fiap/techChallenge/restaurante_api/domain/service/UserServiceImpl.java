package br.com.fiap.techChallenge.restaurante_api.domain.service;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.AddressResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.api.exception.BusinessException;
import br.com.fiap.techChallenge.restaurante_api.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Endereco;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import br.com.fiap.techChallenge.restaurante_api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        Usuario user = Usuario.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userType(dto.getUserType())
                .ultimaAlteracao(LocalDateTime.now())
                .address(mapAddress(dto))
                .build();
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setUserType(dto.getUserType());
        user.setAddress(mapAddress(dto));
        user.setUltimaAlteracao(LocalDateTime.now());

        userRepository.save(user);
        return mapToResponse(user);
    }


    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToResponse(Usuario user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .userType(user.getUserType())
                //.lastUpdate(user.getLastUpdate())
                .address(AddressResponseDTO.builder()
                        .city(user.getAddress().getCidade())
                        .state(user.getAddress().getEstado())
                        .zipCode(user.getAddress().getCep())
                        .street(user.getAddress().getLogradouro())
                        .build())
                .build();
    }

    private Endereco mapAddress(UserRequestDTO dto) {
        return Endereco.builder()
                .logradouro(dto.getAddress().getStreet())
                .cidade(dto.getAddress().getCity())
                .estado(dto.getAddress().getState())
                .cep(dto.getAddress().getZipCode())
                .build();
    }

    @Override
    public void updatePassword(UUID userId, PasswordUpdateRequestDTO dto) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setUltimaAlteracao(LocalDateTime.now());

        userRepository.save(user);
    }

}
