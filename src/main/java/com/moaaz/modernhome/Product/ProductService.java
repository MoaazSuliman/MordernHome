package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Category.Category;
import com.moaaz.modernhome.Category.CategoryServiceImp;
import com.moaaz.modernhome.S3.S3Service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ProductService {


	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryServiceImp categoryServiceImp;

	@Autowired
	private S3Service s3Service;
	@Autowired
	private ProductMapper productMapper;

	public ProductResponse addProduct(ProductRequest productRequest) {
		Product product = productMapper.toEntity(productRequest);
		product.setImages(getImagesUrl(product.getImages()));
		Product save = productRepository.save(product);
		return productMapper.toResponse(save);

	}

	public ProductResponse updateProduct(ProductRequest productRequest, long productId) {
		Product product = getProductById(productId);
		Category category = categoryServiceImp.getById(productRequest.getCategoryId());
		product.setName(productRequest.getName());
		product.setDetails(productRequest.getDetails());
		product.setPrice(productRequest.getPrice());
		product.setDiscount(productRequest.getDiscount());
		product.setCategory(category);
		product.setImages(getImagesUrl(productRequest.getImages()));


		return productMapper.toResponse(productRepository.save(product));

	}

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

	public List<ProductResponse> getAllByCategoryId(long categoryId) {
		Category category = categoryServiceImp.getById(categoryId);
		return category.getProducts()
				.stream()
				.map(productMapper::toResponse).toList();
	}


	public void deleteProduct(long productId) {

		Product product = getProductById(productId);// check if product if exist or throw exception
		product.setDeleted(true);
		productRepository.save(product);


	}

	public ProductResponse getProductResponseById(long productId) {
		return productMapper.toResponse(getProductById(productId));
	}

	public Product getProductById(long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new NoSuchElementException("There Are No Product With id = " + productId)
		);
	}


	public List<ProductResponse> getAll() {
		return productRepository.findAllByIsDeleted(false)
				.stream()
				.map(productMapper::toResponse).toList();
	}


	public List<String> getImagesUrl(List<String> images) {
		return images.stream().map(this::uploadImageAndGetUrl).toList();
	}

	public String uploadImageAndGetUrl(String image) {
		return (!image.startsWith("https")) ? s3Service.uploadImageToS3AndGetImageUrl(image) : image;
	}
}
