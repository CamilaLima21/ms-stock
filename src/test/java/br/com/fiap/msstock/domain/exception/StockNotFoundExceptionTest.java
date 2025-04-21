package br.com.fiap.msstock.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StockNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Stock not found!";
        StockNotFoundException exception = new StockNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }
}
