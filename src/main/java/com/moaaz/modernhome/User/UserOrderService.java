package com.moaaz.modernhome.User;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.moaaz.modernhome.Order.Order;
import com.moaaz.modernhome.Order.OrderResponse;
import com.moaaz.modernhome.Order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserOrderService {

    @Autowired
    private UserService userService;

    public List<OrderResponse> getAllOrdersForUser(long userId) {
        User user = userService.getUserById(userId);
        return rearrangeOrders(user.getOrders()).stream().map(order -> OrderResponse.convertOrderToOrderResponse(order))
                .toList();

    }

    public List<Order> rearrangeOrders(List<Order> orders) {
        ArrayList<Order> allOrders = new ArrayList<>();
        List<Order> inWaitingOrders = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.IN_WAITING)).toList();
        List<Order> inDeliveryOrders = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.IN_DELIVERY)).toList();
        List<Order> completedOrders = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.COMPLETED)).toList();
        allOrders.addAll(inWaitingOrders);
        allOrders.addAll(inDeliveryOrders);
        allOrders.addAll(completedOrders);
        return new ArrayList<>(allOrders);
    }
}
