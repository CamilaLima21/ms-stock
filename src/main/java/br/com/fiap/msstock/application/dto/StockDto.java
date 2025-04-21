package br.com.fiap.msstock.application.dto;

public record StockDto(
        Long id,
        String sku,
        Integer quantity
) {}
