package br.com.fiap.msstock.infrastructure.persistence.repository;

import br.com.fiap.msstock.infrastructure.persistence.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findBySku(String sku);
    boolean existsBySku(String sku);
}
