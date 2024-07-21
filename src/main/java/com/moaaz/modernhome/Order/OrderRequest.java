package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.ProductCart.ProductCart;
import com.moaaz.modernhome.ProductCart.ProductCartRequest;
import com.moaaz.modernhome.ProductCart.ProductCartResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {


	@NotNull(message = "productCartRequests can't be null")
	@Size(min = 0 , message = "you should send at least one product in the list")
	private List<ProductCartRequest> productCartRequests;

	@NotNull(message = "userId can't be null")
	private long userId;

}
