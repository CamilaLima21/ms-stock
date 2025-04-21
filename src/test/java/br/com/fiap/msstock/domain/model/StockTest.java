package br.com.fiap.msstock.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StockTest {

    @Test
    void shouldCreateStockWithValidValues() {
        Long id = 1L;
        String sku = "SKU123456";
        Integer quantity = 50;

        Stock stock = new Stock(id, sku, quantity);

        assertEquals(id, stock.getId());
        assertEquals(sku, stock.getSku());
        assertEquals(quantity, stock.getQuantity());
    }

    @Test
    void shouldAllowNullIdForNewStock() {
        String sku = "SKU789101";
        Integer quantity = 30;

        Stock stock = new Stock(null, sku, quantity);

        assertNull(stock.getId());
        assertEquals(sku, stock.getSku());
        assertEquals(quantity, stock.getQuantity());
    }

    @Test
    void shouldAllowZeroQuantity() {
        Stock stock = new Stock(2L, "SKU000000", 0);

        assertEquals(0, stock.getQuantity());
    }

    @Test
    void shouldHandleNegativeQuantityIfNeeded() {
        Stock stock = new Stock(3L, "SKU999999", -10);

        assertEquals(-10, stock.getQuantity());
    }
}
