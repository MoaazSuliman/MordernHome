package com.moaaz.modernhome.Order;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	Order toEntity(OrderRequest orderRequest);
	OrderResponse toResponse(Order order);
}
