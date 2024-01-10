package com.moaaz.modernhome.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersResponse {


    private List<OrderResponse> orders;
    private double total;

//    public static OrdersResponse convertOrderListToOrdersResponse(List<OrderResponse> orderResponses) {
//        return OrdersResponse
//                .builder()
//                .orders(orderResponses)
//                .total(orderResponses.stream()
//                        .mapToDouble(OrderResponse::getTotal)
//                        .sum())
//                .build();
//    }
}
