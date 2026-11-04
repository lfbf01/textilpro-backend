package com.projeto.integrador.controller;

import com.projeto.integrador.dto.ProductRequestDTO;
import com.projeto.integrador.exception.ResourceNotFoundException;
import com.projeto.integrador.model.Product;
import com.projeto.integrador.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        return productService.create(productDTO);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productDTO) {
        Product existingProduct = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para atualização com o ID: " + id));

        return productService.update(id, productDTO, existingProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para remoção com o ID: " + id));

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

