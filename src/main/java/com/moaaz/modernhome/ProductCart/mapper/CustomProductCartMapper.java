package com.moaaz.modernhome.ProductCart.mapper;

import com.moaaz.modernhome.Product.service.ProductService;
import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.ProductCart.ProductCartRequest;
import com.moaaz.modernhome.ProductCart.ProductCartResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomProductCartMapper {
	@Autowired
	private  ProductCartMapper productCartMapper;
	@Autowired
	private  ProductService productService;



	public ProductCart toEntity(ProductCartRequest productCartRequest) {
		ProductCart entity = productCartMapper.toEntity(productCartRequest);
		entity.setProduct(productService.getProductById(productCartRequest.getProductId()));
		return entity;
	}

	public List<ProductCart> toEntity(List<ProductCartRequest> productCartRequests) {
		return productCartRequests.stream().map(this::toEntity).collect(Collectors.toList());
	}

	public ProductCartResponse toResponse(ProductCart productCart) {
		return productCartMapper.toResponse(productCart);
	}

	public List<ProductCartResponse> toResponse(List<ProductCart> productCarts) {
		return productCarts.stream().map(this::toResponse).collect(Collectors.toList());
	}


}
