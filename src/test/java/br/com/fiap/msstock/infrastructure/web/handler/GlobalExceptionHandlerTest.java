package br.com.fiap.msstock.infrastructure.web.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.fiap.msstock.domain.exception.StockNotFoundException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleStockNotFoundException() {
        String message = "Stock not found!";
        StockNotFoundException ex = new StockNotFoundException(message);

        ResponseEntity<Object> response = handler.handleStockNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals(message, body.get("message"));
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        String message = "Invalid argument!";
        IllegalArgumentException ex = new IllegalArgumentException(message);

        ResponseEntity<Object> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals(message, body.get("message"));
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new Exception("Some error");

        ResponseEntity<Object> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("Internal server error", body.get("message"));
    }
}
