package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Address;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.User;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
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

    public UserResponseDTO(UserDTO user) {
        this.id = user.id();
        this.name = user.name();
        this.email = user.email();
        this.endereco = user.addressDTO();
        this.login = user.login();
        this.userType = user.userType();
        this.createDate = user.createDate();
    }

    private UUID id;
    private String name;
    private String email;
    private AddressDTO endereco;
    private String login;
    private UserType userType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
}
