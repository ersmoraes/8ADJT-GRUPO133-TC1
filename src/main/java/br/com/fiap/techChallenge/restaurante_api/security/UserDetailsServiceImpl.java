package br.com.fiap.techChallenge.restaurante_api.security;

import br.com.fiap.techChallenge.restaurante_api.domain.model.User;
import br.com.fiap.techChallenge.restaurante_api.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com login: " + login));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getUserType().name()) // Converte o enum para um papel (ex: "CLIENTE", "DONO")
                .build();
    }
}
