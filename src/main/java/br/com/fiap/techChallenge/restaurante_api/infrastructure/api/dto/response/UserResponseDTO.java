package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Endereco;
import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {

    public UserResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.name = usuario.getName();
        this.email = usuario.getEmail();
        this.endereco = usuario.getAddress();
        this.login = usuario.getLogin();
        this.userType = usuario.getUserType();
        this.createDate = usuario.getCreateDate();
    }

    private UUID id;
    private String name;
    private String email;
    private Endereco endereco;
    private String login;
    private UserType userType;
    private Boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;


}
