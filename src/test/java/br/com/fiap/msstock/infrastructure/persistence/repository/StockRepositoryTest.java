package br.com.fiap.msstock.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.fiap.msstock.infrastructure.persistence.entity.StockEntity;

@DataJpaTest
public class StockRepositoryTest {

    @Autowired
    private StockRepository repository;

    @Test
    @DisplayName("Should save and retrieve stock by SKU")
    void shouldSaveAndRetrieveStockBySku() {
        StockEntity entity = new StockEntity(null, "SKU-123", 50);
        repository.save(entity);

        Optional<StockEntity> found = repository.findBySku("SKU-123");
        assertTrue(found.isPresent());
        assertEquals(50, found.get().getQuantity());
    }

    @Test
    @DisplayName("Should return empty if stock not found by SKU")
    void shouldReturnEmptyForMissingStockBySku() {
        Optional<StockEntity> result = repository.findBySku("NON-EXISTENT-SKU");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should check existence of stock by SKU")
    void shouldCheckExistenceBySku() {
        StockEntity entity = new StockEntity(null, "SKU-456", 30);
        repository.save(entity);

        boolean exists = repository.existsBySku("SKU-456");
        assertTrue(exists);

        boolean notExists = repository.existsBySku("UNKNOWN-SKU");
        assertFalse(notExists);
    }
}
