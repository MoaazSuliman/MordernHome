package com.moaaz.modernhome.Order;


import com.moaaz.modernhome.Exception.ModernHomeException;
import com.moaaz.modernhome.Mail.OrderMailService;
import com.moaaz.modernhome.Product.service.ProductService;
import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.ProductCart.ProductCartMapper;
import com.moaaz.modernhome.ProductCart.ProductCartRequest;

import com.moaaz.modernhome.User.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {


	private final OrderRepository orderRepository;


	private final UserService userService;

	private final OrderMapper orderMapper;
	private final ProductCartMapper productCartMapper;

	private final OrderMailService orderMailService;


	public OrderResponse addOrder(OrderRequest orderRequest) {
		Order order = orderMapper.toEntity(orderRequest);

		order.setCode(UUID.randomUUID().toString().substring(0, 10));
		order.setStatus(OrderStatus.IN_WAITING);
		order.setUser(userService.getUserById(orderRequest.getUserId()));
		setOrderCart(order, orderRequest);

		Order createdOrder = orderRepository.save(order);
		orderMailService.notifyUser(createdOrder);
		return orderMapper.toResponse(createdOrder);
	}

	private void setOrderCart(Order order, OrderRequest orderRequest) {
		List<ProductCart> productCarts = orderRequest.getProductCartRequests().stream().map(productCartMapper::toEntity).toList();
		order.setProductCarts(productCarts);
	}


	public OrderResponse updateOrder(OrderRequest orderRequest, long orderId) {
		Order existingOrder = getOrderById(orderId);
		canUpdateThisOrder(existingOrder);
		List<ProductCart> productCarts = orderRequest.getProductCartRequests().stream()
				.map(productCartMapper::toEntity).toList();
		// create order from product cart entities.
		existingOrder = Order.builder()
				.id(orderId)
				.productCarts(productCarts)
				.user(userService.getUserById(orderRequest.getUserId()))
				.status(OrderStatus.IN_WAITING)
				.code(UUID.randomUUID().toString().substring(0, 10))
				.build();

		return orderMapper.toResponse(orderRepository.save(existingOrder));
	}

	private void canUpdateThisOrder(Order existingOrder) {
		if (existingOrder.getStatus() != OrderStatus.IN_WAITING)
			throw new ModernHomeException("This Order Can't Be Updated  , It's Out Of Our Modern Home Now.");
	}

	// Make Order In Delivery Status
	public Order acceptOrder(long orderId) {
		Order order = getOrderById(orderId);
		if (order.getStatus() == OrderStatus.IN_WAITING) {
			order.setStatus(OrderStatus.IN_DELIVERY);
			orderMailService.notifyUserOrderIsAccepted(order);
		}
		return orderRepository.save(order);
	}

	// Make Order Completed
	public Order completeOrder(long orderId) {
		Order order = getOrderById(orderId);
		if (order.getStatus() == OrderStatus.IN_DELIVERY) {
			order.setStatus(OrderStatus.COMPLETED);
			orderMailService.notifyUserOrderIsCompleted(order);
		}
		return orderRepository.save(order);
	}

	// return previous status
	public Order getPreviousStatus(long orderId) {
		Order order = getOrderById(orderId);
		if (order.getStatus() == OrderStatus.COMPLETED)
			order.setStatus(OrderStatus.IN_DELIVERY);
		else if (order.getStatus() == OrderStatus.IN_DELIVERY)
			order.setStatus(OrderStatus.IN_WAITING);
		return orderRepository.save(order);

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
