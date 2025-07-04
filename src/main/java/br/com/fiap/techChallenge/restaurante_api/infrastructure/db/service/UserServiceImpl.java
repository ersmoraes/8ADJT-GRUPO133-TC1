package br.com.fiap.techChallenge.restaurante_api.infrastructure.db.service;

import br.com.fiap.techChallenge.restaurante_api.application.repositories.IUserRepository;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model.Address;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserRepository {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model.User newUser =
                br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model.User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .userType(user.getUserType())
                .lastChange(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .address(mapAddress(user.getAddress()))
                .build();
        return mapUser(userRepository.save(newUser));
    }

    private Address mapAddress(br.com.fiap.techChallenge.restaurante_api.domain.entities.Address address) {
        return Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    private br.com.fiap.techChallenge.restaurante_api.domain.entities.Address mapAddress(Address address) {
        return br.com.fiap.techChallenge.restaurante_api.domain.entities.Address.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    private User mapUser(br.com.fiap.techChallenge.restaurante_api.infrastructure.db.model.User user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .userType(user.getUserType())
                .address(mapAddress(user.getAddress()))
                .createDate(user.getCreateDate())
                .lastChange(user.getLastChange())
                .build();
    }

    @Override
    public Optional<User> searchByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> searchByLogin(String login) {
        return Optional.empty();
    }

//    @Override
//    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
//        Usuario user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        if (dto.getName() != null) user.setName(dto.getName());
//        if(dto.getEmail() != null) user.setEmail(dto.getEmail());
//        if(dto.getLogin() != null) user.setLogin(dto.getLogin());
//        if(dto.getUserType() != null) user.setUserType(dto.getUserType());
//        if(dto.getAddress() != null){
//            Endereco endereco = user.getAddress(); // assume que já existe
//            AddressRequestDTO addressDTO = dto.getAddress();
//
//            if (addressDTO.getRua() != null) endereco.setLogradouro(addressDTO.getRua());
//            if (addressDTO.getCidade() != null) endereco.setCidade(addressDTO.getCidade());
//            if (addressDTO.getEstado() != null) endereco.setEstado(addressDTO.getEstado());
//            if (addressDTO.getCep() != null) endereco.setCep(addressDTO.getCep());
//        }
//        user.setCreateDate(LocalDateTime.now());
//        user.setLastChange(LocalDateTime.now());
//
//        userRepository.save(user);
//        return mapToResponse(user);
//    }
//
//
//    public void deleteUser(UUID id) {
//        if (!userRepository.existsById(id)) {
//            throw new ResourceNotFoundException("User not found");
//        }
//        userRepository.deleteById(id);
//    }
//
//    private UserResponseDTO mapToResponse(Usuario user) {
//        return UserResponseDTO.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .login(user.getLogin())
//                .userType(user.getUserType())
//                .createDate(user.getCreateDate())
//                .endereco(Endereco.builder()
//                        .cidade(user.getAddress().getCidade())
//                        .estado(user.getAddress().getEstado())
//                        .cep(user.getAddress().getCep())
//                        .logradouro(user.getAddress().getLogradouro())
//                        .build())
//                .active(true)
//                .build();
//    }
//
//    @Override
//    public void updatePassword(UUID userId, PasswordUpdateRequestDTO dto) {
//        Usuario user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
//
//        if (!dto.getOldPassword().equals(user.getPassword())) {
//            throw new BusinessException("Senha atual incorreta");
//        }
//
//        user.setPassword(dto.getNewPassword());
//        user.setLastChange(LocalDateTime.now());
//        user.setCreateDate(LocalDateTime.now());
//
//        userRepository.save(user);
//    }

}
