package com.moaaz.modernhome.ProductCart.mapper;

import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.ProductCart.ProductCartRequest;
import com.moaaz.modernhome.ProductCart.ProductCartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
 interface ProductCartMapper {


	ProductCart toEntity(ProductCartRequest productCartRequest);

	List<ProductCart> toEntity(List<ProductCartRequest> productCartRequest);

	@Mapping(target = "productId", source = "productCart.product.id")
	ProductCartResponse toResponse(ProductCart productCart);

	List<ProductCartResponse> toResponse(List<ProductCart> productCarts);



}
