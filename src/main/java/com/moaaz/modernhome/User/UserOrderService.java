package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Order.Order;
import com.moaaz.modernhome.Order.OrderResponse;
import com.moaaz.modernhome.Order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserOrderService {

    @Autowired
    private UserServiceImp userServiceImp;

    public List<OrderResponse> getAllOrdersForUser(long userId) {
        User user = userServiceImp.getUserById(userId);
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
