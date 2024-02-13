package com.example.app2.rest;

import com.example.app2.entity.Product;
import com.example.app2.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService service;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProduct() {
        return ResponseEntity.ok(service.getProduct());
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createProduct(@Valid @RequestBody Product product) {
        service.createProduct(product);
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct(@Valid @RequestBody Product product) {
        Objects.requireNonNull(product.getId());
        service.updateProduct(product);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@NotNull @PathVariable Long id) {
        service.deleteProduct(id);
    }



}
