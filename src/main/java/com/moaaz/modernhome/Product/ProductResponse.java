package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Category.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private long id;

	private String name;

	private String details;

	private List<String> images = new ArrayList<>();

	private double price;

	private double discount;

	private double total;

	private LocalDate creationDate;

	private Long categoryId;

	private String categoryName;


	public List<String> getImages() {
		return this.images == null ? new ArrayList<>() : this.images;
	}
}
