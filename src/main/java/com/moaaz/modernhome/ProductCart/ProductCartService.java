package com.moaaz.modernhome.ProductCart;

import com.moaaz.modernhome.Product.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCartService {

    @Autowired
    private ProductServiceImp productServiceImp;

    public  ProductCart convertEntityToDto(ProductCartRequest request) {
        return ProductCart.
                builder()
                .product(productServiceImp.getProductById(request.getProductId()))
                .quantity(request.getQuantity())
                .build();
    }
}
