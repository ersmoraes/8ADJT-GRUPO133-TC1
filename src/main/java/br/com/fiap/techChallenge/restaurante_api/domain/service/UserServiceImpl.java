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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        Usuario user = Usuario.builder()
                .nome(dto.getName())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getPassword()))
                .userType(dto.getUserType())
                .ultimaAlteracao(LocalDateTime.now())
                .endereco(mapAddress(dto))
                .build();
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setNome(dto.getName());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setUserType(dto.getUserType());
        user.setEndereco(mapAddress(dto));
        user.setUltimaAlteracao(LocalDateTime.now());

        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToResponse(Usuario user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getNome())
                .email(user.getEmail())
                .login(user.getLogin())
                .userType(user.getUserType())
                //.lastUpdate(user.getLastUpdate())
                .address(AddressResponseDTO.builder()
                        .city(user.getEndereco().getCidade())
                        .state(user.getEndereco().getEstado())
                        .zipCode(user.getEndereco().getCep())
                        .street(user.getEndereco().getLogradouro())
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
    public void updatePassword(Long userId, PasswordUpdateRequestDTO dto) {
        Usuario user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getSenha())) {
            throw new BusinessException("Senha atual incorreta");
        }

        user.setSenha(passwordEncoder.encode(dto.getNewPassword()));
        user.setUltimaAlteracao(LocalDateTime.now());

        userRepository.save(user);
    }

}
