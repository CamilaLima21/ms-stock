package br.com.fiap.msstock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Stock {
    private Long id;
    private String sku;
    private Integer quantity;
}
