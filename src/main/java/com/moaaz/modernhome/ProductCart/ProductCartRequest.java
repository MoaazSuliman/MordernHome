package com.moaaz.modernhome.ProductCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartRequest {

    private long productId;
    private long quantity;
}
