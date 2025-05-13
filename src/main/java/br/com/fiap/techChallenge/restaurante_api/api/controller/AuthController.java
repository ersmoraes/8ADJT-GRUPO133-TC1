package br.com.fiap.techChallenge.restaurante_api.api.controller;

import br.com.fiap.techChallenge.restaurante_api.api.dto.request.LoginRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.api.dto.response.LoginResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String token = authService.authenticate(loginRequest.getLogin(), loginRequest.getPassword());
        return ResponseEntity.ok(LoginResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .build());
    }
}
