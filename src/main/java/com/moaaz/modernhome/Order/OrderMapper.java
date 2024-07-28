package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.ProductCart.mapper.CustomProductCartMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { CustomProductCartMapper.class })

public interface OrderMapper {



	@Mapping(target = "productCarts", source = "orderRequest.productCartRequests")
	Order toEntity(OrderRequest orderRequest);

	@Mapping(target = "name", source = "order.user.name")
	@Mapping(target = "email", source = "order.user.email")
	@Mapping(target = "phone1", source = "order.user.phone1")
	@Mapping(target = "phone2", source = "order.user.phone2")
	@Mapping(target = "address", source = "order.user.address")
	@Mapping(target = "userId", source = "order.user.id")
	@Mapping(target = "productCartResponses", source = "productCarts")
	OrderResponse toResponse(Order order);
	List<OrderResponse> toResponse(List<Order> order);


}
