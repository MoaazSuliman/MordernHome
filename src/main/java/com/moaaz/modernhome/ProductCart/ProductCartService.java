package com.moaaz.modernhome.ProductCart;

import com.moaaz.modernhome.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCartService {

    @Autowired
    private  ProductService productService;

    public  ProductCart convertEntityToDto(ProductCartRequest request) {
        return ProductCart.
                builder()
                .product(productService.getProductById(request.getProductId()))
                .quantity(request.getQuantity())
                .build();
    }
}
