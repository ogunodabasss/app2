package com.example.app2.services;

import com.example.app2.entity.Cart;
import com.example.app2.entity.Customer;
import com.example.app2.repo.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class CartService {
    private final CartRepository repository;

    public Cart getNextCartCustomer(Long customerId) {
        var cart = Cart.builder()
                .cancel(false)
                .completed(false)
                .totalPrice(BigDecimal.ZERO)
                .customer(Customer.builder().id(customerId).build())
                .build();
        return repository.save(cart);
    }

    public Optional<Cart> getCart(Long id) {
        var res = repository.findById(id);
        System.out.println(res);
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, timeout = 2)
    public void updateByTotalPrice(Long id, BigDecimal totalPrice) {
        repository.updateByTotalPrice(id, totalPrice);
    }

    public Cart updateCart(Cart cart) {
        var beforeCart = repository.findById(cart.getId()).orElseThrow();

        if (Objects.nonNull(cart.getCompleted()))
            beforeCart.setCompleted(cart.getCompleted());
        if (Objects.nonNull(cart.getCancel()))
            beforeCart.setCancel(cart.getCancel());
        return repository.save(beforeCart);
    }

    public void emptyCart(Long customerId) {
        repository.deleteAllByCustomerId(customerId);
    }


}
