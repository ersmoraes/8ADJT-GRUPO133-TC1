package br.com.fiap.techChallenge.restaurante_api.api.dto.response;

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

    private UUID id;
    private String name;
    private String email;
    private AddressResponseDTO address;
    private String login;
    private UserType userType;
    private Boolean Active;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

}
