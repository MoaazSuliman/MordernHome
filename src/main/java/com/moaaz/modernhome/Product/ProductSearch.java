package com.moaaz.modernhome.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSearch {

	private String name;

	private Double priceLessThan;

	private Double priceGreaterThan;

	private Integer lastNumberOfDays;

	private Long categoryId;

	private ProductSort sort;

	private ProductSortBy sortBy;


}
