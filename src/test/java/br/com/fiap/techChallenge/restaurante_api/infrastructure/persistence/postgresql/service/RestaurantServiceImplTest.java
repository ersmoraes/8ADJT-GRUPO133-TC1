package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.service;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.AddressDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.NewRestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.RestaurantDTO;
import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.UserDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.enums.UserType;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.api.exception.ResourceNotFoundException;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.RestaurantEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.UserEntity;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.RestaurantRepository;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private UserRepository userRepository;
    AddressDTO addressDTO = new AddressDTO("Street", "City", "State", "12345-678");

    @Test
    void shouldReturnPageOfRestaurantsWhenFindAllIsCalled() {
        Pageable pageable = mock(Pageable.class);
        Page<RestaurantEntity> restaurantEntities = mock(Page.class);
        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantEntities);
        when(restaurantEntities.map(any())).thenReturn(mock(Page.class));

        Page<RestaurantDTO> result = restaurantService.findAll(pageable);

        assertNotNull(result);
        verify(restaurantRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnRestaurantDTOWhenFindByIdIsCalledWithValidId() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(id);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        restaurantEntity.setOwner(userEntity);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurantEntity));

        Optional<RestaurantDTO> result = restaurantService.findById(id);

        assertTrue(result.isPresent());
        verify(restaurantRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnRestaurantDTOWhenFindByNameIsCalledWithValidId() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(id);
        String name = "Restaurant Name";
        restaurantEntity.setName(name);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName(name);
        restaurantEntity.setOwner(userEntity);
        when(restaurantRepository.findByName(name)).thenReturn(Optional.of(restaurantEntity));

        Optional<RestaurantDTO> result = restaurantService.findByName(name);

        assertTrue(result.isPresent());
        verify(restaurantRepository, times(1)).findByName(name);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeleteRestaurantIsCalledWithInvalidId() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantService.deleteRestaurant(id));
        verify(restaurantRepository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteRestaurantSuccessfullyWhenIdIsValid() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = mock(RestaurantEntity.class);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurantEntity));

        restaurantService.deleteRestaurant(id);

        verify(restaurantRepository, times(1)).delete(restaurantEntity);
    }

    @Test
    void shouldCreateRestaurantSuccessfullyWhenAllFieldsAreValid() {
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO("Restaurant Name", addressDTO, "Delivery",
                "09:00-18:00", userDTOMock(UUID.randomUUID(), addressDTO));

        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(id);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(newRestaurantDTO.owner().id());
        restaurantEntity.setOwner(userEntity);

        when(userRepository.findById(newRestaurantDTO.owner().id())).thenReturn(Optional.of(userEntity));
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);

        RestaurantDTO result = restaurantService.createRestaurant(newRestaurantDTO);

        assertNotNull(result);
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCreateRestaurantIsCalledWithInvalidOwnerId() {
        NewRestaurantDTO newRestaurantDTO = new NewRestaurantDTO("Restaurant Name", addressDTO, "Delivery",
                "09:00", userDTOMock(UUID.randomUUID(), addressDTO));
        when(userRepository.findById(newRestaurantDTO.owner().id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantService.createRestaurant(newRestaurantDTO));
        verify(userRepository, times(1)).findById(newRestaurantDTO.owner().id());
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyWhenAllFieldsAreValid() {
        UUID id = UUID.randomUUID();
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                id,
                "Restaurant Name",
                addressDTO,
                "Delivery",
                "09:00",
                userDTOMock(UUID.randomUUID(), addressDTO));

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(id);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(restaurantDTO.owner().id());
        restaurantEntity.setOwner(userEntity);

        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);

        RestaurantDTO result = restaurantService.updateRestaurant(restaurantDTO);

        assertNotNull(result);
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdateRestaurantIsCalledWithInvalidOwnerId() {
        UUID id = UUID.randomUUID();
        RestaurantDTO restaurantDTO = new RestaurantDTO(
                id,
                "Restaurant Name",
                addressDTO,
                "Delivery",
                "09:00",
                userDTOMock(UUID.randomUUID(), addressDTO));
        when(restaurantRepository.save(any())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> restaurantService.updateRestaurant(restaurantDTO));
        verify(restaurantRepository, times(1)).save(any());
    }

    private static UserDTO userDTOMock(UUID id, AddressDTO addressDTO) {
        return new UserDTO(
                id,
                "name",
                "email@example.com",
                "login",
                "password",
                UserType.OWNER,
                addressDTO,
                null,
                null);
    }
}