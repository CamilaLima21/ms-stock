package br.com.fiap.msstock.application.mapper;

import br.com.fiap.msstock.application.dto.StockDto;
import br.com.fiap.msstock.domain.model.Stock;
import br.com.fiap.msstock.infrastructure.persistence.entity.StockEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockMapperTest {

    private final StockMapper mapper = new StockMapper();

    @Test
    void shouldMapEntityToDomain() {
        StockEntity entity = new StockEntity(1L, "SKU123", 10);
        Stock domain = mapper.toDomain(entity);

        assertEquals(1L, domain.getId());
        assertEquals("SKU123", domain.getSku());
        assertEquals(10, domain.getQuantity());
    }

    @Test
    void shouldMapDomainToEntity() {
        Stock domain = new Stock(2L, "SKU456", 30);
        StockEntity entity = mapper.toEntity(domain);

        assertEquals(2L, entity.getId());
        assertEquals("SKU456", entity.getSku());
        assertEquals(30, entity.getQuantity());
    }

    @Test
    void shouldMapDomainToDto() {
        Stock domain = new Stock(3L, "SKU789", 5);
        StockDto dto = mapper.toDto(domain);

        assertEquals(3L, dto.id());
        assertEquals("SKU789", dto.sku());
        assertEquals(5, dto.quantity());
    }

    @Test
    void shouldMapDtoToDomain() {
        StockDto dto = new StockDto(4L, "SKU101", 40);
        Stock domain = mapper.toDomain(dto);

        assertEquals(4L, domain.getId());
        assertEquals("SKU101", domain.getSku());
        assertEquals(40, domain.getQuantity());
    }
    
    @Test
    void shouldMapEntityToDto() {
        StockEntity entity = new StockEntity(1L, "SKU123", 100);
        StockDto dto = mapper.toDto(entity);

        assertEquals(1L, dto.id());
        assertEquals("SKU123", dto.sku());
        assertEquals(100, dto.quantity());
    }

    @Test
    void shouldMapDtoToEntity() {
        StockDto dto = new StockDto(2L, "SKU456", 200);
        StockEntity entity = mapper.toEntity(dto);

        assertEquals("SKU456", entity.getSku());
        assertEquals(200, entity.getQuantity());
    }
}
