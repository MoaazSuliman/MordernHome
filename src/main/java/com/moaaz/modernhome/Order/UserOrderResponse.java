package com.moaaz.modernhome.Order;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderResponse {

    private List<OrderResponse> inWaitingOrders;
    private List<OrderResponse> inDeliveryOrders;
    private List<OrderResponse> completedOrders;


}
