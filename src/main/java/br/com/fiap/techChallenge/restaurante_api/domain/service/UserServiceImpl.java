package br.com.fiap.techChallenge.restaurante_api.domain.service;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.AddressResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.api.exception.BusinessException;
import br.com.fiap.techChallenge.restaurante_api.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.model.User;
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
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userType(dto.getUserType())
                .lastUpdate(LocalDateTime.now())
                .address(mapAddress(dto))
                .build();
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setUserType(dto.getUserType());
        user.setAddress(mapAddress(dto));
        user.setLastUpdate(LocalDateTime.now());

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

    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .userType(user.getUserType())
                //.lastUpdate(user.getLastUpdate())
                .address(AddressResponseDTO.builder()
                        .city(user.getAddress().getCity())
                        .state(user.getAddress().getState())
                        .zipCode(user.getAddress().getZipCode())
                        .street(user.getAddress().getStreet())
                        .build())
                .build();
    }

    private Address mapAddress(UserRequestDTO dto) {
        return Address.builder()
                .street(dto.getAddress().getStreet())
                .city(dto.getAddress().getCity())
                .state(dto.getAddress().getState())
                .zipCode(dto.getAddress().getZipCode())
                .build();
    }

    @Override
    public void updatePassword(Long userId, PasswordUpdateRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setLastUpdate(LocalDateTime.now());

        userRepository.save(user);
    }

}
