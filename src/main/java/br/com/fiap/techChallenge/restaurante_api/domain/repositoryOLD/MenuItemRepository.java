package br.com.fiap.techChallenge.restaurante_api.domain.repositoryOLD;

import br.com.fiap.techChallenge.restaurante_api.domain.modelOLD.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<ItemCardapio, UUID> {
}
