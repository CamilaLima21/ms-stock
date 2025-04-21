package br.com.fiap.msstock.infrastructure.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.msstock.application.dto.StockDto;
import br.com.fiap.msstock.application.service.StockService;

class StockControllerTest {

    private MockMvc mockMvc;
    private StockService service;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(StockService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StockController(service)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should return all stocks")
    void shouldReturnAllStocks() throws Exception {
        List<StockDto> stocks = List.of(
                new StockDto(1L, "SKU-001", 10),
                new StockDto(2L, "SKU-002", 20)
        );
        when(service.findAll()).thenReturn(stocks);

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return stock by id")
    void shouldReturnStockById() throws Exception {
        when(service.findById(1L)).thenReturn(new StockDto(1L, "SKU-001", 15));

        mockMvc.perform(get("/stocks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    @DisplayName("Should return stock by SKU")
    void shouldReturnStockBySku() throws Exception {
        when(service.findBySku("SKU-001")).thenReturn(new StockDto(1L, "SKU-001", 15));

        mockMvc.perform(get("/stocks/sku/SKU-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    @DisplayName("Should create a new stock")
    void shouldCreateNewStock() throws Exception {
        StockDto request = new StockDto(null, "SKU-NEW", 30);
        StockDto response = new StockDto(10L, "SKU-NEW", 30);

        when(service.create(any(StockDto.class))).thenReturn(response);

        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.sku").value("SKU-NEW"))
                .andExpect(jsonPath("$.quantity").value(30));
    }

    @Test
    @DisplayName("Should update existing stock")
    void shouldUpdateStock() throws Exception {
        StockDto request = new StockDto(null, "SKU-001", 50);
        StockDto response = new StockDto(1L, "SKU-001", 50);

        when(service.update(eq(1L), any(StockDto.class))).thenReturn(response);

        mockMvc.perform(put("/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(50));
    }

    @Test
    @DisplayName("Should delete stock")
    void shouldDeleteStock() throws Exception {
        when(service.delete(1L)).thenReturn("Deleted");

        mockMvc.perform(delete("/stocks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }

    @Test
    @DisplayName("Should decrease stock quantity")
    void shouldDecreaseStock() throws Exception {
        mockMvc.perform(post("/stocks/decrease")
                        .param("sku", "SKU-001")
                        .param("quantity", "5"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should increase stock quantity")
    void shouldIncreaseStock() throws Exception {
        mockMvc.perform(post("/stocks/increase")
                        .param("sku", "SKU-001")
                        .param("quantity", "10"))
                .andExpect(status().isOk());
    }
}

