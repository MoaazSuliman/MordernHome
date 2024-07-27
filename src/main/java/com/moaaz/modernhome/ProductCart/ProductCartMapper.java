package com.moaaz.modernhome.ProductCart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCartMapper {

	ProductCart toEntity(ProductCartRequest productCartRequest);
	ProductCartResponse toResponse(ProductCart productCart);

}
