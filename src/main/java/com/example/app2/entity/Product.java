package com.example.app2.entity;

import com.example.app2.entity.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})

@Entity
@Table
public class Product extends BaseEntity {

    @ElementCollection(fetch = FetchType.LAZY)
    @NotNull.List(value = @NotNull)
    @NotNull
    private final List<PastPrice> pastPrices = new ArrayList<>(0);

    @ToString.Include
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, length = 3)
    private Category category;

    public void addPastPrice(PastPrice pastPrice) {
        pastPrices.add(pastPrice);
    }

    public void removePastPrice(PastPrice pastPrice) {
        pastPrices.remove(pastPrice);
    }


}
