package com.projeto.integrador.service;

import com.projeto.integrador.dto.ProductRequestDTO;
import com.projeto.integrador.model.Product;
import com.projeto.integrador.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // Criação a partir do DTO
    public Product create(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setCategory(dto.getCategory());
        product.setUnitPrice(dto.getUnitPrice());
        product.setDescription(dto.getDescription());

        return productRepository.save(product);
    }

    // Atualização a partir do DTO
    public Product update(Long id, ProductRequestDTO dto, Product existingProduct) {
        existingProduct.setName(dto.getName());
        existingProduct.setSku(dto.getSku());
        existingProduct.setCategory(dto.getCategory());
        existingProduct.setUnitPrice(dto.getUnitPrice());
        existingProduct.setDescription(dto.getDescription());

        return productRepository.save(existingProduct);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}