package com.projeto.integrador.controller;

import com.projeto.integrador.exception.ResourceNotFoundException;
import com.projeto.integrador.model.Inventory;
import com.projeto.integrador.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.findAll();
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Long id) {
        return inventoryService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado com o ID: " + id));
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.save(inventory);
    }

    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        Inventory existingInventory = inventoryService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado para atualização com o ID: " + id));

        existingInventory.setQuantity(inventoryDetails.getQuantity());
        existingInventory.setMinimumThreshold(inventoryDetails.getMinimumThreshold());
        existingInventory.setLocation(inventoryDetails.getLocation());
        existingInventory.setProduct(inventoryDetails.getProduct());

        return inventoryService.save(existingInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registo de stock não encontrado para remoção com o ID: " + id));

        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}