package com.moaaz.modernhome.ProductCart;

import com.moaaz.modernhome.Product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartResponse {

    private long id;
    private long productId;
    private long quantity;

}
