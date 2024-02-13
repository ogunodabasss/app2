package com.example.app2.repo;

import com.example.app2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            select price FROM public.product
            where id = ?1
            """, nativeQuery = true)
    BigDecimal findPriceById(long id);

    @Query(value = """
            select amount FROM public.product
            where id = ?1
            """, nativeQuery = true)
    int findAmountById(long id);

    @Modifying
    @Query(value = """
            UPDATE public.product
            SET amount = amount + ?2
            where id = ?1
            """, nativeQuery = true)
    void updateAmountById(long id, int amount);
}
