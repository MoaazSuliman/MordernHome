package com.moaaz.modernhome.Product;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product toEntity(ProductRequest productRequest);

	ProductResponse toResponse(Product product);
}
