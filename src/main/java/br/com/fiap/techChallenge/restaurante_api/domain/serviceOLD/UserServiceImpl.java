package br.com.fiap.techChallenge.restaurante_api.domain.serviceOLD;

import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

//@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public String login(LoginRequestDTO loginDTO) {
        return "";
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        return null;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        return null;
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }

    @Override
    public void updatePassword(UUID userId, PasswordUpdateRequestDTO dto) {

    }


//
//    private final UserRepository userRepository;
//
//    @Override
//    public Page<Usuario> findAll(Pageable pageable) {
//        return userRepository.findAll(pageable);
//    }
//
//
//    @Override
//    public String login(LoginRequestDTO dto) {
//        Optional<Usuario> usuario = userRepository.findByLoginAndPassword(dto.getLogin(), dto.getPassword());
//        if(!usuario.isPresent()){
//            return "Usuario ou senha invalidos!";
//        }
//        return "Login Efetuado";
//    }
//
//
//    @Override
//    public UserResponseDTO getUserById(UUID id) {
//        Usuario user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//        return mapToResponse(user);
//    }
//
//    @Override
//    public UserResponseDTO createUser(UserRequestDTO dto) {
//        Usuario user = Usuario.builder()
//                .name(dto.getName())
//                .email(dto.getEmail())
//                .login(dto.getLogin())
//                .password(dto.getPassword())
//                .userType(dto.getUserType())
//                .lastChange(LocalDateTime.now())
//                .createDate(LocalDateTime.now())
//                .address(mapAddress(dto))
//                .build();
//        userRepository.save(user);
//        return mapToResponse(user);
//    }
//
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
//    private Endereco mapAddress(UserRequestDTO dto) {
//        return Endereco.builder()
//                .logradouro(dto.getAddress().getRua())
//                .cidade(dto.getAddress().getCidade())
//                .estado(dto.getAddress().getEstado())
//                .cep(dto.getAddress().getCep())
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
