package com.example.app2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true,callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class Cart extends BaseEntity {

    @NotNull
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ToString.Include(name = "id")
    @EqualsAndHashCode.Include
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "cart", cascade = CascadeType.ALL)
    private final Set<Order> orders = new HashSet<>();


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false,length = 1)
    private Boolean completed;

    @Column(nullable = false,length = 1)
    private Boolean cancel;

    public void addOrder(Order order){
        orders.add(order);
        order.setCart(this);
    }

    public void addAllOrder(Collection<? extends Order> c){
        orders.addAll(c);
        c.forEach(order -> order.setCart(this));
    }

    public void removeOrder(Order order){
        orders.remove(order);
        order.setCart(null);
    }

}
