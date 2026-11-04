package com.projeto.integrador.controller;

import com.projeto.integrador.exception.ResourceNotFoundException;
import com.projeto.integrador.model.Order;
import com.projeto.integrador.model.OrderItem;
import com.projeto.integrador.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda não encontrada com o ID: " + id));
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order existingOrder = orderService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda não encontrada para atualização com o ID: " + id));

        existingOrder.setClientName(orderDetails.getClientName());
        existingOrder.setStatus(orderDetails.getStatus());
        existingOrder.setTotalAmount(orderDetails.getTotalAmount());

        if (orderDetails.getItems() != null) {
            existingOrder.getItems().clear();
            existingOrder.getItems().addAll(orderDetails.getItems());
            for (OrderItem item : existingOrder.getItems()) {
                item.setOrder(existingOrder);
            }
        }

        return orderService.save(existingOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda não encontrada para remoção com o ID: " + id));

        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}