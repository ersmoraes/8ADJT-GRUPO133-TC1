package br.com.fiap.techChallenge.restaurante_api.domain.gateway.menuitem;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.domain.entities.Restaurant;

import java.util.UUID;

public interface IMenuItemGateway {

    MenuItem createMenuItem(MenuItem menuItem);

    MenuItem findById(UUID id);

    MenuItem findByName(String name);

    boolean existsByName(String name);
}
