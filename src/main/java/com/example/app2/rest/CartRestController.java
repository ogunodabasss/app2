package com.example.app2.rest;

import com.example.app2.entity.Cart;
import com.example.app2.services.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartRestController {

    private final CartService service;

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cart updateCart(@Valid @RequestBody Cart cart) {
        Objects.requireNonNull(cart.getId());
        return service.updateCart(cart);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Cart>> getCart(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.getCart(id));
    }

    @PostMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> getNextCartCustomer(@NotNull @PathVariable Long customerId) {
        return ResponseEntity.ok(service.getNextCartCustomer(customerId));
    }

    @DeleteMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void emptyCart(@PathVariable @NotNull Long customerId) {
        service.emptyCart(customerId);
    }
}
