package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Category.Category;
import com.moaaz.modernhome.Category.CategoryService;
import com.moaaz.modernhome.S3.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private S3Service s3Service;

    public ProductResponse addProduct(ProductRequest productRequest) {
        if (productRequest.getImages() == null || productRequest.getImages().size() == 0)
            productRequest.setImages(new ArrayList<>());

        return ProductResponse.convertProductToProductResponse(productRepository.save(
                Product
                        .builder()
                        .name(productRequest.getName())
                        .details(productRequest.getDetails())
                        .category(categoryService.getCategoryById(productRequest.getCategoryId()))
                        .images(getImagesUrl(productRequest.getImages()))
                        .price(productRequest.getPrice())
                        .discount(productRequest.getDiscount())
                        .total(calcTotal(productRequest.getPrice(), productRequest.getDiscount()))
                        .creationDate(LocalDate.now())
                        .build()));
    }

    public ProductResponse updateProduct(ProductRequest productRequest, long productId) {
        Product product = getProductById(productId);
        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        product.setName(productRequest.getName());
        product.setDetails(productRequest.getDetails());
        product.setPrice(productRequest.getPrice());
        product.setDiscount(productRequest.getDiscount());
        product.setCategory(category);
        product.setTotal(calcTotal(product.getPrice(), product.getDiscount()));
        product.setImages(getImagesUrl(productRequest.getImages()));

        Product converterProduct = productRepository.save(product);

        return ProductResponse.convertProductToProductResponse(converterProduct);

    }

    public List<ProductResponse> search(String text) {
        String[] strings = text.split(" ");
        Set<Product> allProducts = new HashSet<>();
        for (String string : strings) {
            allProducts.addAll(productRepository.findAllByNameContaining(string));
            allProducts.addAll(productRepository.findAllByDetailsContaining(string));
        }

        return allProducts.stream().
                map(product -> ProductResponse.convertProductToProductResponse(product)).toList();

    }

    public List<ProductResponse> getAllByCategoryId(long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return category.getProducts()
                .stream()
                .map(product -> ProductResponse.convertProductToProductResponse(product)).toList();
    }

    public void deleteImage(long productId, String imageUrl) {
        Product product = getProductById(productId);
        List<String> images = product.getImages();
        for (String image : images) {
            if (image.equals(imageUrl)) {
                images.remove(imageUrl);
                break;
            }
        }
        product.setImages(images);
        productRepository.save(product);
    }


    public void deleteProduct(long productId) {
        log.info("Getting Product *********");
        Product product = getProductById(productId);// check if product if exist or throw exception
        if (product.isDeleted())
            throw new NoSuchElementException("This Product Already Deleted.");
        log.info("Deleting Product From DB. With Id {} and this is product name {}", productId, product.getName());
        product.setDeleted(true);
        productRepository.save(product);
//        productRepository.deleteById(productId);
//        productRepository.delete(product);
        //        productRepository.customDeleteById(productId);
        log.info("Deleted Successfully");
    }

    public ProductResponse getProductResponseById(long productId) {
        return ProductResponse.convertProductToProductResponse(getProductById(productId));
    }

    public Product getProductById(long productId) {
        return productRepository.findById(productId ).orElseThrow(
                () -> new NoSuchElementException("There Are No Product With id = " + productId)
        );
    }

    private double calcTotal(double price, double discount) {

        return price - price * (discount / 100);

    }


    public List<ProductResponse> getAll() {
        return productRepository.findAllByIsDeleted(false).stream().map(product -> ProductResponse.convertProductToProductResponse(product)).toList();
    }


    public List<String> getImagesUrl(List<String> images) {
        return images.stream().map(image -> uploadImageAndGetUrl(image)).toList();
    }

    public String uploadImageAndGetUrl(String image) {
        return (!image.startsWith("https")) ? s3Service.uploadImageToS3AndGetImageUrl(image) : image;
    }
}
