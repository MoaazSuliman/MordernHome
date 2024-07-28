package com.moaaz.modernhome.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product toEntity(ProductRequest productRequest);

	@Mapping(target = "categoryId", source = "product.category.id")
	@Mapping(target = "categoryName", source = "product.category.name")
	ProductResponse toResponse(Product product);


}
