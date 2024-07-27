package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Order.Order;
import com.moaaz.modernhome.Order.OrderMapper;
import com.moaaz.modernhome.Order.OrderResponse;
import com.moaaz.modernhome.Order.OrderStatus;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderService {


	private final UserService userService;
	private final UserMapper userMapper;
	private final OrderMapper orderMapper;

	public List<OrderResponse> getAllOrdersForUser(long userId) {
		User user = userService.getUserById(userId);
		return rearrangeOrders(user.getOrders()).stream().map(orderMapper::toResponse)
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
