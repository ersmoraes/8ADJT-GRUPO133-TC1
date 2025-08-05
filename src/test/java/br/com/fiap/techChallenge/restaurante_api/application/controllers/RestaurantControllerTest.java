package br.com.fiap.techChallenge.restaurante_api.application.controllers;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.restaurant.IRestaurantDataSource;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.user.IUserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

    private IRestaurantDataSource dataSource;
    private IUserDataSource userDataSource;
    private RestaurantController controller;

    AddressDTO addressDTO = new AddressDTO("Rua", "Cidade", "Estado", "12345-678");
    UserDTO userDTO = new UserDTO(
            UUID.randomUUID(),
            "Dono",
            "dono@email.com",
            "login",
            "senha",
            UserType.OWNER,
            addressDTO,
            null,
            null
    );

    @BeforeEach
    void setUp() {
        dataSource = mock(IRestaurantDataSource.class);
        userDataSource = mock(IUserDataSource.class);
        controller = RestaurantController.create(dataSource, userDataSource);
    }

    @Test
    void shouldThrowExceptionWhenCreateRestaurantWithNullDTO() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.createRestaurant(null));
        assertTrue(exception.getMessage().contains("Erro ao criar restaurante"));
    }

    @Test
    void shouldThrowExceptionWhenFindByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> controller.findById(null));
    }

    @Test
    void shouldCreateRestaurantWhenDataIsValid() {
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO(
                "Restaurante",
                addressDTO,
                "Italiana",
                "18:00",
                userDTO
        );

        when(userDataSource.findById(userDTO.id())).thenReturn(Optional.of(userDTO));
        var restaurantDTO = new RestaurantDTO(
                UUID.randomUUID(),
                newRestaurantDTO.name(),
                newRestaurantDTO.address(),
                newRestaurantDTO.kitchenType(),
                newRestaurantDTO.openingHours(),
                newRestaurantDTO.owner());
        when(dataSource.createRestaurant(newRestaurantDTO)).thenReturn(restaurantDTO);

        assertDoesNotThrow(() -> controller.createRestaurant(newRestaurantDTO));
    }

    @Test
    void shouldDeleteRestaurantWithoutException() {
        IRestaurantDataSource restaurantDataSource = mock(IRestaurantDataSource.class);
        IUserDataSource userDataSource = mock(IUserDataSource.class);
        RestaurantController controller = RestaurantController.create(restaurantDataSource, userDataSource);

        UUID id = UUID.randomUUID();

        assertDoesNotThrow(() -> controller.deleteRestaurant(id));
    }

    @Test
    void shouldThrowExceptionWhenDeleteRestaurantWithNullId() {
        IRestaurantDataSource restaurantDataSource = mock(IRestaurantDataSource.class);
        IUserDataSource userDataSource = mock(IUserDataSource.class);
        RestaurantController controller = RestaurantController.create(restaurantDataSource, userDataSource);

        assertThrows(IllegalArgumentException.class, () -> controller.deleteRestaurant(null));
    }

    @Test
    void shouldReturnPageOfRestaurantsWhenFindAllIsCalled() {
        Pageable pageable = mock(Pageable.class);
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                UUID.randomUUID(), "Restaurante", addressDTO, "Italiana", "18:00", userDTO);
        Page<RestaurantDTO> page = new PageImpl<>(List.of(restaurantDTO));
        when(dataSource.findAll(pageable)).thenReturn(page);
        controller = RestaurantController.create(dataSource, userDataSource);
        assertDoesNotThrow(() -> controller.findAll(pageable));
    }

    @Test
    void shouldReturnRestaurantDTOWhenFindByNameIsValid() {
        String name = "Restaurante";
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                UUID.randomUUID(), name, addressDTO, "Italiana", "18:00", userDTO);
        when(dataSource.findByName(name)).thenReturn(Optional.of(restaurantDTO));

        assertDoesNotThrow(() -> controller.findByName(name));
    }

    @Test
    void shouldThrowExceptionWhenFindByNameThrows() {
        String name = "Inexistente";
        when(dataSource.findByName(name)).thenThrow(new IllegalArgumentException("Restaurante não encontrado com o nome: " + name));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.findByName(name));
        assertEquals("Restaurante não encontrado com o nome: " + name, exception.getMessage());
    }

    @Test
    void shouldUpdateRestaurantWhenDataIsValid() {
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                UUID.randomUUID(), "Restaurante", addressDTO, "Italiana", "18:00", userDTO);
        when(dataSource.findById(restaurantDTO.id())).thenReturn(Optional.of(restaurantDTO));
        when(dataSource.updateRestaurant(restaurantDTO)).thenReturn(restaurantDTO);

        assertDoesNotThrow(() -> controller.updateRestaurant(restaurantDTO));
    }

    @Test
    void shouldThrowExceptionWhenUpdateRestaurantThrows() {
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                UUID.randomUUID(), "Restaurante", addressDTO, "Italiana", "18:00", userDTO);
        when(dataSource.updateRestaurant(restaurantDTO)).thenThrow(new IllegalArgumentException("Erro"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                controller.updateRestaurant(restaurantDTO));
        assertTrue(exception.getMessage().contains("Erro ao atualizar restaurante"));
    }
}