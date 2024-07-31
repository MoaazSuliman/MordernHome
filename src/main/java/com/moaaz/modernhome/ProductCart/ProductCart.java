package com.moaaz.modernhome.ProductCart;

import com.moaaz.modernhome.Product.Product;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "product_in_cart")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCart {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Product product;

	private long quantity;

	private double total;

	public double getTotal() {
		return this.product.getTotal() * quantity;
	}


}
