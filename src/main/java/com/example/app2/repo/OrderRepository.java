package com.example.app2.repo;

import com.example.app2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
            SELECT * FROM "order"
            WHERE customer_id = ?1
            """, nativeQuery = true)
    List<Order> findAllByCustomerId(Long customerId);

    Optional<Order> findByProductId(Long productId);

    @Modifying
    @Query(value = """
            UPDATE public."order"
            SET total = total -1
            WHERE product_id = ?1
            """, nativeQuery = true)
    void updateTotalByProductId(Long productId);

    @Modifying
    @Query(value = """
            DELETE FROM public."order"
            WHERE product_id = ?1
            """, nativeQuery = true)
    void deleteByProductId(Long productId);
}
