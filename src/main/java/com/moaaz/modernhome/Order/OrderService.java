package com.moaaz.modernhome.Order;


import com.moaaz.modernhome.Product.ProductService;
import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.ProductCart.ProductCartRequest;

import com.moaaz.modernhome.User.UserService;
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
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    public Order addOrder(OrderRequest orderRequest) {

        // 1- convert product in cart list request to entities.
        List<ProductCart> productCarts = orderRequest.getProductCartRequests().stream()
                .map(productCartRequest -> convertProductCartRequestToProductCart(productCartRequest)).toList();
        // create order from product cart entities.
        Order order = Order.builder()
                .productCarts(productCarts)
                .user(userService.getUserById(orderRequest.getUserId()))
                .status(OrderStatus.IN_WAITING)
                .localDate(LocalDate.now())
                .code(UUID.randomUUID().toString().substring(0, 10))
                .build();
        // calc order total.
        order.setTotal(calcOrderRequestTotal(order));

        return orderRepository.save(order);


    }

    // Make Order In Delivery Status
    public Order acceptOrder(long orderId) {
        Order order = getOrderById(orderId);
        if (order.getStatus() == OrderStatus.IN_WAITING)
            order.setStatus(OrderStatus.IN_DELIVERY);
        return orderRepository.save(order);
    }

    // Make Order Completed
    public Order completeOrder(long orderId) {
        Order order = getOrderById(orderId);
        if (order.getStatus() == OrderStatus.IN_DELIVERY)
            order.setStatus(OrderStatus.COMPLETED);
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
        log.info("{0}" + searchRequest);
        searchRequest.getOptimizedSearchRequest();
        log.info("{0}" + searchRequest);
        return orderRepository.getAllOrdersFromDateToDateWithStatus
                        (searchRequest.getFromDate(),
                                searchRequest.getToDate(),
                                searchRequest.getOrderStatus())
                .stream()
                .map(OrderResponse::convertOrderToOrderResponse)
                .toList();
    }

    public List<?> getAll() {
        log.info("Getting All*****************************************************");
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responses = orders.stream()
                .map(order -> OrderResponse.convertOrderToOrderResponse(order))
                .toList();
        responses.forEach(
                response -> log.info(response.getCode() + "**************")
        );
        return responses;
    }

    private Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("There Are No Order With Id = " + orderId)
        );
    }

    private String dateOfNow() {
        // Create a LocalDate object
        LocalDate date = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Format the LocalDate as a string
        return date.format(formatter);


    }

    private ProductCart convertProductCartRequestToProductCart(ProductCartRequest productCartRequest) {
        return ProductCart.builder()
                .product(productService.getProductById(productCartRequest.getProductId()))
                .quantity(productCartRequest.getQuantity())
                .build();

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
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There Are No Order With Id = " + id)
        );
        return OrderResponse.convertOrderToOrderResponse(order);

    }

    // delete from order TODO


    // update order quantity that are in the order TODO
}
