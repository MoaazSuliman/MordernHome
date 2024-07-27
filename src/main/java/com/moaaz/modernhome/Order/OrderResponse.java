package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.ProductCart.ProductCartResponse;
import com.moaaz.modernhome.User.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@ToString
public class OrderResponse {

    private long id;

    private String code;

    private List<ProductCartResponse> productCartResponses;

    private LocalDate creationTime;

    private String name;

    private String email;

    private String phone1;

    private String phone2;

    private String address;

    private double total;

    private OrderStatus status;

    private long userId;


}
