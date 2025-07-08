package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.gateway;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.IUserGateway;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserGatewayImpl implements IUserGateway {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        UserEntity newUser = UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .userType(user.getUserType())
                .addressEntity(AddressEntity.toAddressEntity(user.getAddress()))
                .createDate(user.getCreateDate())
                .lastChange(user.getLastChange())
                .build();
        return User.toUser(userRepository.save(newUser));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsByLogin(String login) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<User> searchByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> searchByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .map(User::toUser)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


//    @Override
//    public String login(LoginRequestDTO dto) {
//        Optional<Usuario> usuario = userRepository.findByLoginAndPassword(dto.getLogin(), dto.getPassword());
//        if(!usuario.isPresent()){
//            return "Usuario ou senha invalidos!";
//        }
//        return "Login Efetuado";
//    }

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
