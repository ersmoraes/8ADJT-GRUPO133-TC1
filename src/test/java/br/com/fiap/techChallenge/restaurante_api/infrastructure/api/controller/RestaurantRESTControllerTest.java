package br.com.fiap.techChallenge.restaurante_api.infrastructure.api.controller;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.AddressRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.request.RestaurantRequestDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.dto.response.RestaurantResponseDTO;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantRESTControllerTest {

    @Mock
    RestaurantRepository restaurantService;
    @Mock
    UserRepository userService;
    AddressDTO addressDTO = new AddressDTO("Street", "City", "State", "12345-678");
    UserDTO userDTO = new UserDTO(
            UUID.randomUUID(),
            "Paulo cesar",
            "paulo@gmail.com",
            "paulocesar",
            "123456",
            UserType.OWNER,
            addressDTO,
            null,
            null);

    @Test
    void shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        RestaurantDTO mockResponse = new RestaurantDTO(
                id,
                "Paulo cesar",
                addressDTO,
                "Italiana",
                "18:00",
                userDTO);

        when(restaurantService.findById(id)).thenReturn(Optional.of(mockResponse));

        RestaurantRESTController controller = new RestaurantRESTController(restaurantService, userService);
        ResponseEntity<RestaurantResponseDTO> response = controller.findById(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(mockResponse.id(), response.getBody().getId());
    }

    @Test
    void shouldReturnNoContentWhenUserIsDeleted() {
        UUID id = UUID.randomUUID();
        RestaurantRESTController controller = new RestaurantRESTController(restaurantService, userService);

        ResponseEntity<Void> response = controller.deleteRestaurant(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldUpdateUserAndReturnUpdatedUserResponseDTO() {
        UUID id = UUID.randomUUID();
        RestaurantRequestDTO restaurantRequestDTO = new RestaurantRequestDTO();
        restaurantRequestDTO.setName("Novo Nome");
        restaurantRequestDTO.setKitchenType("Janponesa");
        restaurantRequestDTO.setOpeningHours("16:00");
        restaurantRequestDTO.setOwnerId(userDTO.id());
        RestaurantDTO updatedRestaurantDTO = new RestaurantDTO(
                id,
                "Paulo cesar",
                addressDTO,
                "Italiana",
                "18:00",
                userDTO);

        when(restaurantService.updateRestaurant(any(RestaurantDTO.class))).thenReturn(updatedRestaurantDTO);
        when(restaurantService.findById(any())).thenReturn(Optional.of(updatedRestaurantDTO));

        RestaurantRESTController controller = new RestaurantRESTController(restaurantService, userService);
        ResponseEntity<RestaurantResponseDTO> response = controller.updateRestaurant(id, restaurantRequestDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Paulo cesar", response.getBody().getName());
        assertEquals("Italiana", response.getBody().getKitchenType());
        assertEquals("18:00", response.getBody().getOpeningHours());
    }

    @Test
    void shouldCreateUserAndReturnUserResponseDTO() {
        var restaurantRequestDTO = new RestaurantRequestDTO();
        restaurantRequestDTO.setName("Fulano");
        var address = new AddressRequestDTO();
        address.setStreet("Street");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345-678");
        restaurantRequestDTO.setAddress(address);
        restaurantRequestDTO.setKitchenType("Italiana");
        restaurantRequestDTO.setOpeningHours("18:00");
        restaurantRequestDTO.setOwnerId(userDTO.id());
        RestaurantDTO createdRestaurantDTO = new RestaurantDTO(
                UUID.randomUUID(),
                "Fulano",
                addressDTO,
                "Italiana",
                "18:00",
                userDTO);

        when(restaurantService.createRestaurant(any(NewRestaurantDTO.class))).thenReturn(createdRestaurantDTO);
        when(userService.findById(restaurantRequestDTO.getOwnerId())).thenReturn(Optional.of(userDTO));

        RestaurantRESTController controller = new RestaurantRESTController(restaurantService, userService);
        ResponseEntity<RestaurantResponseDTO> response = controller.createRestaurant(restaurantRequestDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Fulano", response.getBody().getName());
        assertEquals("Italiana", response.getBody().getKitchenType());
        assertEquals("18:00", response.getBody().getOpeningHours());
    }
}