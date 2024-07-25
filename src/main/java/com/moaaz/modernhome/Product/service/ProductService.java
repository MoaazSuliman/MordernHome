package com.moaaz.modernhome.Product.service;

import com.moaaz.modernhome.Product.Product;
import com.moaaz.modernhome.Product.ProductRequest;
import com.moaaz.modernhome.Product.ProductResponse;
import com.moaaz.modernhome.Product.ProductSearch;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
	ProductResponse addProduct(ProductRequest productRequest);
	ProductResponse updateProduct(ProductRequest productRequest, long productId);
	List<ProductResponse> search(String text);
	List<ProductResponse> getAllByCategoryId(long categoryId);
	void deleteProduct(long productId);
	ProductResponse getProductResponseById(long productId);
	Product getProductById(long productId);
	List<ProductResponse> getAll();
	Page<ProductResponse> getAll(ProductSearch productSearch , Pageable pageable);
	List<String> getImagesUrl(List<String> images);
	String uploadImageAndGetUrl(String image);
}
