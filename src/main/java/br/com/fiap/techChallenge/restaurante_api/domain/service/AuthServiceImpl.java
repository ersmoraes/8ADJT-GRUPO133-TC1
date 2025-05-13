package br.com.fiap.techChallenge.restaurante_api.service.impl;

import br.com.fiap.techChallenge.restaurante_api.domain.model.User;
import br.com.fiap.techChallenge.restaurante_api.domain.repository.UserRepository;
import br.com.fiap.techChallenge.restaurante_api.security.JwtTokenProvider;
import br.com.fiap.techChallenge.restaurante_api.domain.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String authenticate(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return jwtTokenProvider.generateToken(user);
    }
}
