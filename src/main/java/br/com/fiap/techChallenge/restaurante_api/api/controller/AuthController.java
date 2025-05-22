package br.com.fiap.techChallenge.restaurante_api.api.controller;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.LoginResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.UserResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "Autenticação e Autorização de usuários")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login",
            description = "Realiza o login do usuário e retorna um token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Login ou senha inválidos"),
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login do usuário", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequestDTO.class)))
            @RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            String token = authService.authenticate(loginRequest.getLogin(), loginRequest.getPassword());
            return ResponseEntity.ok(LoginResponseDTO.builder().status("Login Efetuado Com Sucesso!")
                    .token(token)
                    .type("Bearer")
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Login ou senha inválidos.");
        }
    }

}
