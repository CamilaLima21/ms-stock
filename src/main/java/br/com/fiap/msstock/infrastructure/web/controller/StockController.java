package br.com.fiap.msstock.infrastructure.web.controller;

import br.com.fiap.msstock.application.dto.StockDto;
import br.com.fiap.msstock.application.service.StockService;
import br.com.fiap.msstock.domain.exception.StockNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StockDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<StockDto> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(service.findBySku(sku));
    }

    @PostMapping
    public ResponseEntity<StockDto> create(@Valid @RequestBody StockDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDto> update(@PathVariable long id, @Valid @RequestBody StockDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping("/decrease")
    public ResponseEntity<Void> decrease(@RequestParam String sku, @RequestParam int quantity) {
        service.decreaseStock(sku, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<Void> increase(@RequestParam String sku, @RequestParam int quantity) {
        service.increaseStock(sku, quantity);
        return ResponseEntity.ok().build();
    }
}
