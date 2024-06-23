package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.ProductCart.ProductCartRequest;
import com.moaaz.modernhome.ProductCart.ProductCartResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {


    private List<ProductCartRequest>productCartRequests;

    private long userId;

}
