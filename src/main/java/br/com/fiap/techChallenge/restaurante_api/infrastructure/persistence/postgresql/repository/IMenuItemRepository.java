package br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.repository;

import br.com.fiap.techChallenge.restaurante_api.domain.entities.MenuItem;
import br.com.fiap.techChallenge.restaurante_api.infrastructure.persistence.postgresql.model.MenuItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface IMenuItemRepository extends JpaRepository<MenuItemEntity, UUID> {

    MenuItem findByName(String name);

    boolean existsByName(String name);

    @Modifying
    @Query("""
        UPDATE MenuItemEntity m SET 
            m.name = :name,
            m.description = :description,
            m.price = :price,
            m.onlyLocal = :onlyLocal,
            m.urlFoto = :urlFoto
        WHERE m.id = :id
    """)
    void update(UUID id, String name, String description, BigDecimal price, boolean onlyLocal, String urlFoto);
}
