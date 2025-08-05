package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.application.usecases.restaurant.CreateRestaurantUseCase;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateItemMenuUseCaseTest {

    @Test
    void createSucessItemMenu(){
        IMenuItemGateway itemGateway = mock(IMenuItemGateway.class);
        MenuItemDTO menuItemDTO = new MenuItemDTO(null,"x-burger", "x-burger with cheese", new BigDecimal("19.90"), true, "C:/");
        UUID id = UUID.randomUUID();
        MenuItem menuItem =  MenuItem.create(
                id,
                "x-burger",
                "x-burger with cheese",
                new BigDecimal("19.90"),
                true,
                "C:/"
        );

        when(itemGateway.createMenuItem(any(MenuItem.class))).thenReturn(menuItem);

        CreateMenuItemUseCase createMenuItemUseCase = CreateMenuItemUseCase.create(itemGateway);

        MenuItem result = createMenuItemUseCase.execute(menuItemDTO);

        assertEquals(menuItem, result);
        assertNotNull(result.getId());
    }
}
