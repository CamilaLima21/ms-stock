package br.com.fiap.msstock.application.service;

import br.com.fiap.msstock.application.dto.StockDto;
import br.com.fiap.msstock.application.mapper.StockMapper;
import br.com.fiap.msstock.domain.exception.StockNotFoundException;
import br.com.fiap.msstock.infrastructure.persistence.entity.StockEntity;
import br.com.fiap.msstock.infrastructure.persistence.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository repository;
    private final StockMapper mapper;

    public StockService(StockRepository repository, StockMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public StockDto create(StockDto dto) {
        if (repository.existsBySku(dto.sku())) {
            throw new IllegalArgumentException("SKU already registered in stock.");
        }

        var entity = mapper.toEntity(mapper.toDomain(dto));
        return mapper.toDto(mapper.toDomain(repository.save(entity)));
    }

    public List<StockDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public StockDto findById(long id) {
        StockEntity entity = repository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock with ID " + id + " not found."));
        return mapper.toDto(mapper.toDomain(entity));
    }

    public StockDto update(long id, StockDto dto) {
        StockEntity entity = repository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock with ID " + id + " not found."));

        entity.setQuantity(dto.quantity());

        return mapper.toDto(mapper.toDomain(repository.save(entity)));
    }

    public String delete(long id) {
        StockEntity entity = repository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock with ID " + id + " not found."));
        repository.delete(entity);
        return "Stock deleted successfully!";
    }

    public StockDto findBySku(String sku) {
        StockEntity entity = repository.findBySku(sku)
                .orElseThrow(() -> new StockNotFoundException("Stock with SKU " + sku + " not found."));
        return mapper.toDto(mapper.toDomain(entity));
    }

    public void decreaseStock(String sku, int quantity) {
        StockEntity entity = repository.findBySku(sku)
                .orElseThrow(() -> new StockNotFoundException("Stock with SKU " + sku + " not found."));

        if (entity.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for SKU: " + sku);
        }

        entity.setQuantity(entity.getQuantity() - quantity);
        repository.save(entity);
    }

    public void increaseStock(String sku, int quantity) {
        StockEntity entity = repository.findBySku(sku)
                .orElseThrow(() -> new StockNotFoundException("Stock with SKU " + sku + " not found."));

        entity.setQuantity(entity.getQuantity() + quantity);
        repository.save(entity);
    }
}
