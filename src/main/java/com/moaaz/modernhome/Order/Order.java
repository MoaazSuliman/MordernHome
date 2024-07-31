package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.User.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.cdi.Eager;

@Entity
@Table(name = "user_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@Column(unique = true)
	private String code;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductCart> productCarts;


	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate creationTime;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;


	@Transient
	private double total;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;


	public double getTotal() {
		return this.productCarts.stream().mapToDouble(ProductCart::getTotal).sum();
	}

}
