package com.moaaz.modernhome.Product.service;

import com.moaaz.modernhome.Category.Category;
import com.moaaz.modernhome.Category.CategoryService;
import com.moaaz.modernhome.Category.CategoryServiceImp;
import com.moaaz.modernhome.Product.Product;
import com.moaaz.modernhome.Product.ProductCriteriaBuilder;
import com.moaaz.modernhome.Product.ProductMapper;
import com.moaaz.modernhome.Product.ProductRepository;
import com.moaaz.modernhome.Product.ProductRequest;
import com.moaaz.modernhome.Product.ProductResponse;
import com.moaaz.modernhome.Product.ProductSearch;
import com.moaaz.modernhome.S3.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {



	private final ProductRepository productRepository;

	private final S3Service s3Service;

	private final ProductMapper productMapper;
	private final CategoryService categoryService;

	@Override
	public ProductResponse addProduct(ProductRequest productRequest) {
		Product product = productMapper.toEntity(productRequest);
		product.setImages(getImagesUrl(product.getImages()));
		product.setCategory(categoryService.getById(productRequest.getCategoryId()));
		Product save = productRepository.save(product);
		return productMapper.toResponse(save);

	}

	@Override
	public ProductResponse updateProduct(ProductRequest productRequest, long productId) {
		Product product = getProductById(productId);
		Category category = categoryService.getById(productRequest.getCategoryId());
		product.setName(productRequest.getName());
		product.setDetails(productRequest.getDetails());
		product.setPrice(productRequest.getPrice());
		product.setDiscount(productRequest.getDiscount());
		product.setCategory(category);
		product.setImages(getImagesUrl(productRequest.getImages()));


		return productMapper.toResponse(productRepository.save(product));

	}

	@Override
	public List<ProductResponse> search(String text) {
		String[] strings = text.split(" ");
		Set<Product> allProducts = new HashSet<>();
		for (String string : strings) {
			allProducts.addAll(productRepository.findAllByNameContaining(string));
			allProducts.addAll(productRepository.findAllByDetailsContaining(string));
		}

		return allProducts.stream().
				map(productMapper::toResponse).toList();

	}

	@Override
	public List<ProductResponse> getAllByCategoryId(long categoryId) {
		Category category = categoryService.getById(categoryId);
		return category.getProducts()
				.stream()
				.map(productMapper::toResponse).toList();
	}


	@Override
	public void deleteProduct(long productId) {
		Product product = getProductById(productId);// check if product if exist or throw exception
		product.setDeleted(true);
		productRepository.save(product);

	}

	@Override
	public ProductResponse getProductResponseById(long productId) {
		return productMapper.toResponse(getProductById(productId));
	}

	@Override
	public Product getProductById(long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new NoSuchElementException("There Are No Product With id = " + productId)
		);
	}

	@Override
	public List<ProductResponse> getAll() {
		return productRepository.findAllByIsDeleted(false)
				.stream()
				.map(productMapper::toResponse).toList();
	}

	@Override
	public Page<ProductResponse> getAll(ProductSearch productSearch , Pageable pageable) {
		Page<Product> page = productRepository.findAll(ProductCriteriaBuilder.getProductPredicate(productSearch), pageable);
		List<ProductResponse> responses = page.stream().map(productMapper::toResponse).toList();

		return new PageImpl<>(responses, pageable, page.getTotalElements());
	}

	@Override
	public List<String> getImagesUrl(List<String> images) {
		return images.stream().map(this::uploadImageAndGetUrl).toList();
	}

	@Override
	public String uploadImageAndGetUrl(String image) {
		return (!image.startsWith("https")) ? s3Service.uploadImageToS3AndGetImageUrl(image) : image;
	}
}
