package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.docs;

import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.PasswordUpdateRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.UserRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface UserDocs {

    @Operation(summary = "Buscar todos os Usuarios",
            description = "Retorna todos os usuarios cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuários não encontrado")
            })
    @GetMapping
    ResponseEntity<Page<UserResponseDTO>> findAll(
            @Parameter(description = "Parâmetros de paginação: número da página (page), tamanho da página (size) e ordenação (sort). Exemplo: ?page=0&size=10&sort=name,asc",
                    example = "page=0&size=10&sort=name,asc")
            Pageable pageable);

    @Operation(summary = "Login",
            description = "Realiza o Login do usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login e senha Validos"),
                    @ApiResponse(responseCode = "401", description = "Login ou senha Invalidos")
            })
    ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequestDTO.class)))
            @RequestBody LoginRequestDTO dto);

    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário pelo seu UUID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    ResponseEntity<UserResponseDTO> findById(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id);

    @Operation(summary = "Criar novo usuário",
            description = "Cria um novo usuário com os dados informados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário criado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
            })
    ResponseEntity<UserResponseDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            @RequestBody UserRequestDTO userRequestDTO);

    @Operation(summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            @RequestBody UserRequestDTO userRequestDTO);

    @Operation(summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    ResponseEntity<Void> updatePassword(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da nova senha", required = true,
                    content = @Content(schema = @Schema(implementation = PasswordUpdateRequestDTO.class)))
            @RequestBody PasswordUpdateRequestDTO dto);

    @Operation(summary = "Deletar usuário",
            description = "Remove um usuário pelo seu UUID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id);
}
