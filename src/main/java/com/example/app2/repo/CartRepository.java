package com.example.app2.repo;

import com.example.app2.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


    Optional<Cart> findByCustomerId(Long customerId);

    @Modifying
    @Query(value = """
            DELETE FROM public.cart
            WHERE
            customer_id = ?1
            """, nativeQuery = true)
    void deleteAllByCustomerId(Long customerId);

    @Modifying
    @Query(value = """
            UPDATE public.cart
            SET total_price = total_price + ?2
            WHERE id = ?1
            """, nativeQuery = true)
    void updateByTotalPrice(Long id, BigDecimal totalPrice);

}
