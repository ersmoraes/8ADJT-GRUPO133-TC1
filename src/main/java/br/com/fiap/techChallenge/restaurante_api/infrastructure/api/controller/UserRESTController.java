package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.controllers.UserController;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.docs.UserDocs;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.AddressRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "Gerenciamento de usuários")
public class UserRESTController implements UserDocs {

    private final UserController userController;

    public UserRESTController(UserRepository userService) {
        this.userController = UserController.create(userService);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable) {
        Page<UserResponseDTO> dtoPage = userController.findAll(pageable).map(UserResponseDTO::new);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        try {
            String mensagem = userController.login(dto.getLogin(), dto.getPassword());
            return ResponseEntity.ok().body(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        UserResponseDTO user = new UserResponseDTO(userController.findById(id));
        return ResponseEntity.ok(user);
    }

    @Override
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        NewUserDTO newUserDTO = new NewUserDTO(
                userRequestDTO.getName(),
                userRequestDTO.getEmail(),
                userRequestDTO.getLogin(),
                userRequestDTO.getPassword(),
                userRequestDTO.getUserType(),
                parseAddresToAddresDTO(userRequestDTO.getAddress())
        );
        return ResponseEntity.ok(parseDTO(userController.createUser(newUserDTO)));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO) {
        UserDTO userResponse = userController.updateUser(new UserDTO(id, userRequestDTO.getName(), userRequestDTO.getEmail(),
                userRequestDTO.getLogin(), userRequestDTO.getPassword(), userRequestDTO.getUserType(),
                parseAddresToAddresDTO(userRequestDTO.getAddress()), null, null));

        return ResponseEntity.ok(parseDTO(userResponse));
    }

    @Override
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody PasswordUpdateRequestDTO dto) {
        userController.updatePassword(new UserDTO(id, null, null, null, dto.getNewPassword(),
                null, null, null, null), dto.getOldPassword());
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userController.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private static AddressDTO parseAddresToAddresDTO(AddressRequestDTO addressRequestDTO) {
        if (addressRequestDTO == null || addressRequestDTO.getStreet() == null) {
            return new AddressDTO(null, null, null, null);
        }
        return new AddressDTO(addressRequestDTO.getStreet(), addressRequestDTO.getCity(),
                addressRequestDTO.getState(), addressRequestDTO.getZipCode());
    }

    private UserResponseDTO parseDTO(UserDTO userDTO) {
        return new UserResponseDTO(
                userDTO.id(),
                userDTO.name(),
                userDTO.email(),
                userDTO.addressDTO(),
                userDTO.login(),
                userDTO.userType(),
                userDTO.createDate()
        );
    }
}
