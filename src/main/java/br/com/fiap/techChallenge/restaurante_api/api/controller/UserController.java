package br.com.fiap.techChallenge.restaurante_api.api.controller;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import br.com.fiap.techChallenge.restaurante_api.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "users", description = "Gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Buscar todos os Usuarios",
            description = "Retorna todos os usuarios cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuários não encontrado")
            })
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable){
        Page<Usuario> usuarios = userService.findAll(pageable);
        Page<UserResponseDTO> dtoPage = usuarios.map(UserResponseDTO::new);
        return ResponseEntity.ok(dtoPage);
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
        UserResponseDTO user = userService.getUserById(id);
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
        return ResponseEntity.ok(userService.createUser(userRequestDTO));
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
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
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
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da nova senha", required = true,
                    content = @Content(schema = @Schema(implementation = PasswordUpdateRequestDTO.class)))
            @RequestBody PasswordUpdateRequestDTO dto) {
        userService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Login",
            description = "Realiza o Login do usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login e senha Validos"),
                    @ApiResponse(responseCode = "401", description = "Login ou senha Invalidos")
            })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        String mensagem = userService.login(dto);
        if(mensagem.contains("invalidos")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mensagem);
        }
        return ResponseEntity.ok().body(mensagem);
    }
}
