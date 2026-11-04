package com.projeto.integrador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    @NotBlank(message = "O SKU é obrigatório")
    private String sku;

    @NotBlank(message = "A categoria é obrigatória")
    private String category;

    @NotNull(message = "O preço unitário é obrigatório")
    @Positive(message = "O preço unitário deve ser superior a zero")
    private BigDecimal unitPrice;

    private String description;
}