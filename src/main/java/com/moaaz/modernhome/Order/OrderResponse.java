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

    public static OrderResponse convertOrderToOrderResponse(Order order) {
        log.info(order.getCode() + "********************************************************");
        User user = order.getUser();
        return OrderResponse
                .builder()
                .id(order.getId())
                .name(user.getName())
                .phone1(user.getPhone1())
                .phone2(user.getPhone2())
                .address(user.getAddress())
                .email(user.getEmail())
                .productCartResponses(order.getProductCarts().stream().map(ProductCartResponse::convertProductCartToProductCartResponse).toList())
                .creationTime(order.getCreationTime())
                .total(order.getTotal())
                .status(order.getStatus())
                .code(order.getCode())
                .build();
    }

}
