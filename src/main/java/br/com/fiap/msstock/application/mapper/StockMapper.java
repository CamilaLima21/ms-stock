package br.com.fiap.msstock.application.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.msstock.application.dto.StockDto;
import br.com.fiap.msstock.domain.model.Stock;
import br.com.fiap.msstock.infrastructure.persistence.entity.StockEntity;

@Component
public class StockMapper {

    public Stock toDomain(StockEntity entity) {
        return new Stock(entity.getId(), entity.getSku(), entity.getQuantity());
    }

    public StockEntity toEntity(Stock domain) {
        StockEntity entity = new StockEntity();
        entity.setId(domain.getId());
        entity.setSku(domain.getSku());
        entity.setQuantity(domain.getQuantity());
        return entity;
    }

    public StockDto toDto(Stock domain) {
        return new StockDto(domain.getId(), domain.getSku(), domain.getQuantity());
    }

    public Stock toDomain(StockDto dto) {
        return new Stock(dto.id(), dto.sku(), dto.quantity());
    }

    public StockDto toDto(StockEntity entity) {
        return new StockDto(entity.getId(), entity.getSku(), entity.getQuantity());
    }

    public StockEntity toEntity(StockDto dto) {
        StockEntity entity = new StockEntity();
        entity.setSku(dto.sku());
        entity.setQuantity(dto.quantity());
        return entity;
    }
}
