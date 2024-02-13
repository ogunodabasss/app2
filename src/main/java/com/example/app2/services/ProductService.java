package com.example.app2.services;

import com.example.app2.entity.PastPrice;
import com.example.app2.entity.Product;
import com.example.app2.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Product> getProduct() {
        var sort = Sort.by(Sort.Order.asc("id"));
        return productRepository.findAll(sort);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public BigDecimal findPriceById(long id) {
        return productRepository.findPriceById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int findAmountById(long id) {
        return productRepository.findAmountById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, timeout = 2)
    public void updateAmountById(long id, int amount) {
        productRepository.updateAmountById(id, amount);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        var beforeProduct = productRepository.findById(product.getId()).orElseThrow();
        if (Objects.nonNull(product.getAmount())) beforeProduct.setAmount(product.getAmount());
        if (Objects.nonNull(product.getCategory())) beforeProduct.setCategory(product.getCategory());
        if (Objects.nonNull(product.getName())) beforeProduct.setName(product.getName());
        if (Objects.nonNull(product.getPrice())) {

            beforeProduct.addPastPrice(PastPrice.builder().price(beforeProduct.getPrice()).build());
            beforeProduct.setPrice(product.getPrice());
        }
        var _ = productRepository.save(beforeProduct);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
