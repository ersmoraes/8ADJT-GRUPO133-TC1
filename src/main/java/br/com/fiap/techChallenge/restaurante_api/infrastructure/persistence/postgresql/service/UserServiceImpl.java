package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UpdatePasswordUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserDataSource;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.BusinessException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.enums.UserTypeEnum;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.AddressEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements IUserDataSource {

    private final UserRepository userRepository;

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDTO::toUserDTO);
    }

    public String login(LoginRequestDTO dto) {
        Optional<UserDTO> usuario = this.findByLoginAndPassword(dto.getLogin(), dto.getPassword());
        if (usuario.isEmpty()) {
            return "Usuario ou senha invalidos!";
        }
        return "Login Efetuado com sucesso!";
    }

    @Override
    public Optional<UserDTO> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .map(UserDTO::toUserDTO);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::toUserDTO);
    }

    @Override
    public Optional<UserDTO> findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(UserDTO::toUserDTO);
    }

    @Override
    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findById(id)
                .map(UserDTO::toUserDTO);
    }

    @Override
    public UserDTO createUser(NewUserDTO user) {
        UserEntity userEntity = new UserEntity(
                null,
                user.name(),
                user.email(),
                user.login(),
                user.password(),
                UserTypeEnum.fromString(user.userType().name()),
                LocalDateTime.now(),
                null,
                new AddressEntity(user.address().street(),
                        user.address().city(),
                        user.address().state(),
                        user.address().zipCode())
        );
        return UserDTO.toUserDTO(userRepository.save(userEntity));
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        UserEntity userUpdated = userRepository.save(UserEntity.toUserEntityFromDTO(dto));
        return UserDTO.toUserDTO(userUpdated);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updatePassword(UpdatePasswordUserDTO dto) {
        UserEntity user = userRepository.findByLogin(dto.login())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        user.setPassword(dto.newPassword());
        user.setLastChange(LocalDateTime.now());

        return UserDTO.toUserDTO(userRepository.save(user));
    }

}
