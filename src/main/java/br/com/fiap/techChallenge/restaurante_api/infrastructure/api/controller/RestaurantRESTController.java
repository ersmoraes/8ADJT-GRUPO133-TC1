package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.controllers.RestaurantController;
import br.com.fiap.techChallenge.restaurante_api.application.controllers.UserController;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.AddressRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.RestaurantRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.RestaurantResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "restaurants", description = "Gerenciamento de restaurantes")
public class RestaurantRESTController {

    private final RestaurantController restaurantController;
    private final UserController userController;

    public RestaurantRESTController(RestaurantRepository restaurantService, UserRepository userService) {
        this.restaurantController = RestaurantController.create(restaurantService, userService);
        this.userController = UserController.create(userService);
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponseDTO>> findAll(Pageable pageable) {
        Page<RestaurantResponseDTO> dtoPage = restaurantController.findAll(pageable).map(RestaurantResponseDTO::new);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable UUID id) {
        RestaurantResponseDTO restaurantDTO = new RestaurantResponseDTO(restaurantController.findById(id));
        return ResponseEntity.ok(restaurantDTO);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody @Valid RestaurantRequestDTO dto) {
        if (dto == null || dto.getOwnerId() == null) {
            throw new IllegalArgumentException("Dados do dono não podem ser nulos");
        }
        UserDTO userDTO = userController.findById(dto.getOwnerId());
        if (userDTO == null) {
            throw new IllegalArgumentException("Usuário dono não existe com o id: " +
                    dto.getOwnerId());
        }
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO(
                dto.getName(),
                parseAddresToAddresDTO(dto.getAddress()),
                dto.getKitchenType(),
                dto.getOpeningHours(),
                userDTO
        );
        return ResponseEntity.ok(parseDTO(restaurantController.createRestaurant(newRestaurantDTO)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable UUID id, @RequestBody @Valid RestaurantRequestDTO dto) {
        RestaurantDTO restaurantUpdate = new RestaurantDTO(id, dto.getName(), parseAddresToAddresDTO(dto.getAddress()),
                dto.getKitchenType(), dto.getOpeningHours(), new UserDTO(dto.getOwnerId(), null, null, null, null, null, null, null, null));
        RestaurantDTO restaurantDTO = restaurantController.updateRestaurant(restaurantUpdate);
        return ResponseEntity.ok(parseDTO(restaurantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID id) {
        restaurantController.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    private static AddressDTO parseAddresToAddresDTO(AddressRequestDTO addressRequestDTO) {
        if (addressRequestDTO == null) {
            return null;
        }
        return new AddressDTO(addressRequestDTO.getStreet(), addressRequestDTO.getCity(),
                addressRequestDTO.getState(), addressRequestDTO.getZipCode());
    }

    private RestaurantResponseDTO parseDTO(RestaurantDTO restaurantDTO) {
        return new RestaurantResponseDTO(
                restaurantDTO.id(),
                restaurantDTO.name(),
                restaurantDTO.addressDTO(),
                restaurantDTO.kitchenType(),
                restaurantDTO.openingHours(),
                restaurantDTO.owner()
        );
    }
}