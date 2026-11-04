package com.projeto.integrador.service;

import com.projeto.integrador.model.Inventory;
import com.projeto.integrador.model.Order;
import com.projeto.integrador.model.OrderItem;
import com.projeto.integrador.repository.InventoryRepository;
import com.projeto.integrador.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional // Garante que se houver erro, nada é gravado (rollback automático)
    public Order save(Order order) {
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);

                // Procura o registo de stock para o produto
                Inventory inventory = inventoryRepository.findByProduct(item.getProduct())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado no stock: " + item.getProduct().getName()));

                // Valida se há quantidade suficiente em stock
                if (inventory.getQuantity() < item.getQuantity()) {
                    throw new RuntimeException("Stock insuficiente para o produto: " + item.getProduct().getName() +
                            ". Disponível: " + inventory.getQuantity() + ", Solicitado: " + item.getQuantity());
                }

                // Efetua a baixa automática no stock
                inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                inventoryRepository.save(inventory);
            }
        }
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}