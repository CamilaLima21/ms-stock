package br.com.fiap.msstock.application.service;

import br.com.fiap.msstock.application.dto.StockDto;
import br.com.fiap.msstock.application.mapper.StockMapper;
import br.com.fiap.msstock.domain.exception.StockNotFoundException;
import br.com.fiap.msstock.infrastructure.persistence.entity.StockEntity;
import br.com.fiap.msstock.infrastructure.persistence.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    private StockRepository repository;
    private StockMapper mapper;
    private StockService service;

    @BeforeEach
    void setUp() {
        repository = mock(StockRepository.class);
        mapper = new StockMapper();
        service = new StockService(repository, mapper);
    }

    @Test
    void shouldFindAllStock() {
        when(repository.findAll()).thenReturn(List.of(new StockEntity(1L, "SKU123", 10)));

        List<StockDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("SKU123", result.get(0).sku());
        assertEquals(10, result.get(0).quantity());
    }

    @Test
    void shouldFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(new StockEntity(1L, "SKU001", 20)));

        StockDto result = service.findById(1L);

        assertEquals("SKU001", result.sku());
        assertEquals(20, result.quantity());
    }

    @Test
    void shouldThrowWhenIdNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    void shouldFindBySku() {
        when(repository.findBySku("SKU001")).thenReturn(Optional.of(new StockEntity(1L, "SKU001", 30)));

        StockDto result = service.findBySku("SKU001");

        assertEquals(30, result.quantity());
        assertEquals("SKU001", result.sku());
    }

    @Test
    void shouldThrowWhenSkuNotFound() {
        when(repository.findBySku("NOT_FOUND")).thenReturn(Optional.empty());

        assertThrows(StockNotFoundException.class, () -> service.findBySku("NOT_FOUND"));
    }

    @Test
    void shouldUpdateStock() {
        StockDto dto = new StockDto(1L, "SKU999", 99);
        StockEntity entity = new StockEntity(1L, "SKU999", 10);
        StockEntity savedEntity = new StockEntity(1L, "SKU999", 99);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(savedEntity);

        StockDto result = service.update(1L, dto);

        assertEquals(99, result.quantity());
        assertEquals("SKU999", result.sku());
    }

    @Test
    void shouldCreateNewStock() {
        StockDto dto = new StockDto(null, "NEWSKU", 50);  // ID deve ser null aqui
        StockEntity savedEntity = new StockEntity(1L, "NEWSKU", 50);

        when(repository.existsBySku("NEWSKU")).thenReturn(false);
        when(repository.save(any())).thenReturn(savedEntity);

        StockDto result = service.create(dto);

        assertEquals("NEWSKU", result.sku());
        assertEquals(50, result.quantity());
    }

    @Test
    void shouldThrowWhenCreatingStockWithExistingSku() {
        StockDto dto = new StockDto(null, "DUPSKU", 100); // ID deve ser null aqui
        when(repository.existsBySku("DUPSKU")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
    }

    @Test
    void shouldDeleteStock() {
        StockEntity entity = new StockEntity(1L, "SKU001", 10);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        String result = service.delete(1L);

        assertEquals("Stock deleted successfully!", result);
        verify(repository).delete(entity);
    }

    @Test
    void shouldDecreaseStock() {
        StockEntity entity = new StockEntity(1L, "SKU123", 50);
        when(repository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        service.decreaseStock("SKU123", 20);

        verify(repository).save(argThat(e -> e.getQuantity() == 30));
    }

    @Test
    void shouldThrowWhenDecreasingTooMuchStock() {
        StockEntity entity = new StockEntity(1L, "SKU123", 10);
        when(repository.findBySku("SKU123")).thenReturn(Optional.of(entity));

        assertThrows(IllegalArgumentException.class, () -> service.decreaseStock("SKU123", 20));
    }

    @Test
    void shouldIncreaseStock() {
        StockEntity entity = new StockEntity(1L, "SKU456", 10);
        when(repository.findBySku("SKU456")).thenReturn(Optional.of(entity));

        service.increaseStock("SKU456", 15);

        verify(repository).save(argThat(e -> e.getQuantity() == 25));
    }
}
