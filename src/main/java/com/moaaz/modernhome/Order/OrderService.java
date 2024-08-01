package com.moaaz.modernhome.Order;


import com.moaaz.modernhome.Exception.ModernHomeException;
import com.moaaz.modernhome.Mail.OrderMailService;
import com.moaaz.modernhome.ProductCart.mapper.CustomProductCartMapper;
import com.moaaz.modernhome.User.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {


	private final OrderRepository orderRepository;


	private final UserService userService;

	private final OrderMapper orderMapper;


	private final OrderMailService orderMailService;


	public OrderResponse addOrder(OrderRequest orderRequest) {

		Order order = orderMapper.toEntity(orderRequest);


		order.setCode(UUID.randomUUID().toString().substring(0, 10));
		order.setStatus(OrderStatus.IN_WAITING);
		order.setUser(userService.getUserById(orderRequest.getUserId()));


		Order createdOrder = orderRepository.save(order);
		orderMailService.notifyUser(createdOrder);

		return orderMapper.toResponse(createdOrder);


	}


	public OrderResponse updateOrder(OrderRequest orderRequest, long orderId) {
		Order existingOrder = getOrderById(orderId);
		Order comingOrder = orderMapper.toEntity(orderRequest);
		canUpdateThisOrder(existingOrder);

		// update status, product in cart.
		existingOrder = Order.builder()
				.id(orderId)
				.productCarts(comingOrder.getProductCarts())
				.status(OrderStatus.IN_WAITING)
				.build();

		return orderMapper.toResponse(orderRepository.save(existingOrder));
	}

	private void canUpdateThisOrder(Order existingOrder) {
		if (existingOrder.getStatus() != OrderStatus.IN_WAITING)
			throw new ModernHomeException("This Order Can't Be Updated  , It's Out Of Our Modern Home Now.");
	}

	// Make Order In Delivery Status
	public void acceptOrder(long orderId) {
		Order order = getOrderById(orderId);
		if (OrderStatus.IN_WAITING.equals(order.getStatus())) {
			order.setStatus(OrderStatus.IN_DELIVERY);
			orderMailService.notifyUserOrderIsAccepted(order);
		}
		orderRepository.save(order);
	}

	// Make Order Completed
	public void completeOrder(long orderId) {
		Order order = getOrderById(orderId);
		if (OrderStatus.IN_DELIVERY.equals(order.getStatus())) {
			order.setStatus(OrderStatus.COMPLETED);
			orderMailService.notifyUserOrderIsCompleted(order);
		}
		orderRepository.save(order);
	}

	// return previous status
	public void getPreviousStatus(long orderId) {
		Order order = getOrderById(orderId);
		if (OrderStatus.COMPLETED.equals(order.getStatus()))
			order.setStatus(OrderStatus.IN_DELIVERY);
		else if (OrderStatus.IN_DELIVERY.equals(order.getStatus()))
			order.setStatus(OrderStatus.IN_WAITING);
		orderRepository.save(order);

	}
//    public Order

	// get all completed orders from date to date that have status
	public List<OrderResponse> getAllOrdersFromDateToDateWithStatus(SearchRequest searchRequest) {

		searchRequest.getOptimizedSearchRequest();
		return orderRepository.getAllOrdersFromCreationTimeToCreationTimeWithStatus
						(searchRequest.getFromDate(),
								searchRequest.getToDate(),
								searchRequest.getOrderStatus())
				.stream()
				.map(orderMapper::toResponse)
				.toList();
	}

	public List<?> getAll() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream()
				.map(orderMapper::toResponse)
				.toList();
	}

	private Order getOrderById(long orderId) {
		return orderRepository.findById(orderId).orElseThrow(
				() -> new NoSuchElementException("There Are No Order With Id = " + orderId)
		);
	}


	private double calcOrderRequestTotal(Order order) {

		return order.getProductCarts().stream()
				.mapToDouble(productCart ->
						productCart
								.getProduct()
								.getTotal() * productCart.getQuantity())
				.sum();

	}

	public OrderResponse getById(long id) {
		return orderMapper.toResponse(getOrderById(id));
	}

}
