package com.moaaz.modernhome.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {


	private String name;

	private String details;

	private List<String> images = new ArrayList<>();

	private double price;


	private double discount = 0.0;

	private long categoryId;
}
