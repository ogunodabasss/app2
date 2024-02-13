package com.example.app2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

@Accessors(fluent = false)
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "\"order\"")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    @ToString.Include(name = "id")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @Column(nullable = false)
    private Integer total;

    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @JsonIgnore
    @ToString.Include(name = "id")
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false),
            @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    })
    private Cart cart;


}
