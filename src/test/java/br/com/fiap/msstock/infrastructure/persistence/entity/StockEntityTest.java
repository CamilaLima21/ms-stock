package br.com.fiap.msstock.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StockEntityTest {

    @Test
    void shouldCreateEntityWithAllArgsConstructor() {
        Long id = 1L;
        String sku = "SKU-001";
        Integer quantity = 100;

        StockEntity entity = new StockEntity(id, sku, quantity);

        assertEquals(id, entity.getId());
        assertEquals(sku, entity.getSku());
        assertEquals(quantity, entity.getQuantity());
    }

    @Test
    void shouldSetValuesUsingSetters() {
        StockEntity entity = new StockEntity();
        entity.setId(2L);
        entity.setSku("SKU-002");
        entity.setQuantity(50);

        assertEquals(2L, entity.getId());
        assertEquals("SKU-002", entity.getSku());
        assertEquals(50, entity.getQuantity());
    }

    @Test
    void shouldHandleNullValuesGracefully() {
        StockEntity entity = new StockEntity(null, null, null);

        assertNull(entity.getId());
        assertNull(entity.getSku());
        assertNull(entity.getQuantity());
    }
}
