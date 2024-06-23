package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String code;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<ProductCart> productCarts;

    private LocalDate localDate;

    @ManyToOne
    private User user;

    private double total;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
