package com.example.app2.rest;

import com.example.app2.entity.Cart;
import com.example.app2.entity.Order;
import com.example.app2.services.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService service;

    @GetMapping(value = "/getAllOrdersForCustomer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getAllOrdersForCustomer(customerId));
    }


    @GetMapping(value = "/getOrderForCode/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrderForCode(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderForCode(id));
    }

    @PutMapping(value = "/placeOrder/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cart placeOrder(@PathVariable @NotNull Long cartId) {
        return service.placeOrder(cartId);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order addProductToCart(@Valid @RequestBody Order order) {
       return service.addProductToCart(order);
    }

    @DeleteMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeProductFromCart(@NotNull @PathVariable Long productId) {
        service.removeProductFromCart(productId);
    }

}
