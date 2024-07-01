package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Order.Order;
import com.moaaz.modernhome.Order.OrderResponse;
import com.moaaz.modernhome.Order.OrderStatus;
import com.moaaz.modernhome.security.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String name;

    private String email;
    private String phone1;
    private String phone2;
    private String address;
    private String password;

    private boolean active;
    private UserRole role;



    private List<OrderResponse> inWaitingOrders;

    private List<OrderResponse> inDeliveryOrders;
    private List<OrderResponse> completedOrders;


    public static UserResponse convertUserToUserResponse(User user) {

        if (user.getOrders() == null)
            user.setOrders(new ArrayList<>());
        return UserResponse.
                builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone1(user.getPhone1())
                .phone2(user.getPhone2())
                .role(user.getRole())
                .address(user.getAddress())
                .active(user.isActive())
                .inWaitingOrders(separateOrders(user, OrderStatus.IN_WAITING).stream().map(OrderResponse::convertOrderToOrderResponse).toList())
                .inDeliveryOrders(separateOrders(user, OrderStatus.IN_DELIVERY).stream().map(OrderResponse::convertOrderToOrderResponse).toList())
                .completedOrders(separateOrders(user, OrderStatus.COMPLETED).stream().map(OrderResponse::convertOrderToOrderResponse).toList())
                .build();


    }

    private static List<Order> separateOrders(User user, OrderStatus status) {
        List<Order> separateOrder = new ArrayList<>();
        for (Order order : user.getOrders())
            if (order.getStatus() == status)
                separateOrder.add(order);
        return separateOrder;
    }

}
