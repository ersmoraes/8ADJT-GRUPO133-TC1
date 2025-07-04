package br.com.fiap.techChallenge.restaurante_api.apiOLD.dto.request;

import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String login;
    private String password;
    private AddressRequestDTO address;
    private UserType userType;
}
