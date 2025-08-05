package br.com.fiap.techChallenge.restaurante_api.application.usecases.menuItem;

import br.com.fiap.techChallenge.restaurante_api.application.presenters.dto.MenuItemDTO;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem.IMenuItemGateway;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FindAllMenuItemUseCaseTest {

    @Test
    void findAllTest() {
        IMenuItemGateway itemGateway = mock(IMenuItemGateway.class);

        FindAllMenuItemUseCase useCase =  FindAllMenuItemUseCase.create(itemGateway);

        UUID id = UUID.randomUUID();
        MenuItem menuItem = MenuItem.create(
                id,
                "x-burger",
                "x-burger with cheese",
                new BigDecimal("19.90"),
                true,
                "C:/"
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<MenuItem> expectedPage = new PageImpl<>(List.of(menuItem), pageable, 1);

        when(itemGateway.findAll(pageable)).thenReturn(expectedPage);

        // Execução do caso de uso
        Page<MenuItem> result = useCase.execute(pageable);

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("x-burger", result.getContent().get(0).getName());
        assertEquals(new BigDecimal("19.90"), result.getContent().get(0).getPrice());

        // Verifica se o gateway foi chamado corretamente
        verify(itemGateway, times(1)).findAll(pageable);
    }
}
