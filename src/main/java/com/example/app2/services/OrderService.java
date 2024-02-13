package com.example.app2.services;

import com.example.app2.entity.Cart;
import com.example.app2.entity.Order;
import com.example.app2.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ConfigurableApplicationContext context;

    public Cart placeOrder(Long cartId) {
       return context.getBean(CartService.class).updateCart(Cart.builder()
                .id(cartId)
                .completed(true)
                .build());
    }

    public String getOrderForCode(Long id) {
        return orderRepository.findById(id).orElse(Order.builder().code(null).build()).getCode();
    }

    public List<Order> getAllOrdersForCustomer(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    public Order addProductToCart(Order order) {
        if (order.getTotal() == 0) throw new RuntimeException();
        if (context.getBean(ProductService.class).findAmountById(order.getProduct().getId()) - order.getTotal() <= 0) {
            throw new RuntimeException();
        }

        var afterOrder = orderRepository.save(order);
        context.getBean(ProductService.class).updateAmountById(afterOrder.getProduct().getId(), -afterOrder.getTotal());
        BigDecimal price = context.getBean(ProductService.class).findPriceById(afterOrder.getProduct().getId());
        var totalPrice = price.multiply(BigDecimal.valueOf(order.getTotal()));
        context.getBean(CartService.class).updateByTotalPrice(afterOrder.getCart().getId(), totalPrice);
        return afterOrder;
    }

    public void removeProductFromCart(Long productId) {
        var order = orderRepository.findByProductId(productId).orElseThrow();
        if (order.getTotal() != 0) {
            orderRepository.updateTotalByProductId(productId);
            context.getBean(ProductService.class).updateAmountById(productId, 1);

            BigDecimal price = context.getBean(ProductService.class).findPriceById(productId);
            context.getBean(CartService.class).updateByTotalPrice(order.getCart().getId(), price);

        } else {
            orderRepository.deleteByProductId(productId);
            context.getBean(ProductService.class).updateAmountById(productId, order.getTotal());
            BigDecimal price = context.getBean(ProductService.class).findPriceById(productId);
            var totalPrice = price.multiply(BigDecimal.valueOf(order.getTotal()));
            context.getBean(CartService.class).updateByTotalPrice(order.getCart().getId(), totalPrice);
        }
    }

}
