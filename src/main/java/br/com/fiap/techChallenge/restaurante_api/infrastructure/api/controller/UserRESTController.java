package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.controllers.UserController;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewUserDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.AddressRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class UserRESTController {

    private final UserController userController;

    public UserRESTController(UserServiceImpl userService) {
        this.userController = UserController.create(userService);
    }

    @Operation(summary = "Buscar todos os Usuarios",
            description = "Retorna todos os usuarios cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuários não encontrado")
            })
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(
            @Parameter(description = "Parâmetros de paginação: número da página (page), tamanho da página (size) e ordenação (sort). Exemplo: ?page=0&size=10&sort=nome,asc",
                    example = "page=0&size=10&sort=nome,asc")
            Pageable pageable) {
        Page<UserResponseDTO> dtoPage = userController.findAll(pageable).map(UserResponseDTO::new);
        return ResponseEntity.ok(dtoPage);
    }

    @Operation(summary = "Login",
            description = "Realiza o Login do usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login e senha Validos"),
                    @ApiResponse(responseCode = "401", description = "Login ou senha Invalidos")
            })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequestDTO.class)))
            @RequestBody LoginRequestDTO dto) {
        String mensagem = userController.login(dto.getLogin(), dto.getPassword());
        if (mensagem.contains("invalidos")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensagem);
        }
        return ResponseEntity.ok().body(mensagem);
    }

    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário pelo seu UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id) {
        UserResponseDTO user = new UserResponseDTO(userController.findById(id));
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Criar novo usuário",
            description = "Cria um novo usuário com os dados informados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário criado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            @RequestBody UserRequestDTO userRequestDTO) {
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

    @Operation(summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            @RequestBody UserRequestDTO userRequestDTO) {
        UserDTO userResponse = userController.updateUser(new UserDTO(id, userRequestDTO.getName(), userRequestDTO.getEmail(),
                userRequestDTO.getLogin(), userRequestDTO.getPassword(), userRequestDTO.getUserType(),
                parseAddresToAddresDTO(userRequestDTO.getAddress()), null, null));

        return ResponseEntity.ok(parseDTO(userResponse));
    }

    @Operation(summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da nova senha", required = true,
                    content = @Content(schema = @Schema(implementation = PasswordUpdateRequestDTO.class)))
            @RequestBody PasswordUpdateRequestDTO dto) {
        userController.updatePassword(new UserDTO(id, null, null, null, dto.getNewPassword(),
                null, null, null, null), dto.getOldPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar usuário",
            description = "Remove um usuário pelo seu UUID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id) {
        userController.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private static AddressDTO parseAddresToAddresDTO(AddressRequestDTO addressRequestDTO) {
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
