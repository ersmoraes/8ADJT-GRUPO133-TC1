package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
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

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.endereco = user.getAddress();
        this.login = user.getLogin();
        this.userType = user.getUserType();
        this.createDate = user.getCreateDate();
    }

    private UUID id;
    private String name;
    private String email;
    private Address endereco;
    private String login;
    private UserType userType;
    private Boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
}
