package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.Product.Product;
import com.moaaz.modernhome.Product.ProductRepository;
import com.moaaz.modernhome.Product.ProductResponse;
import com.moaaz.modernhome.Product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // add
    public Category addCategory(Category category) {
        category.setCreationDate(LocalDate.now());
        return categoryRepository.save(category);
    }

    // update
    public CategoryResponse updateCategory(Category category, long categoryId) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(category.getName());
        existingCategory.setDetails(category.getDetails());
        return convertCategoryToCategoryResponse(categoryRepository.save(existingCategory));
    }

    // delete
    @Transactional
    public void deleteCategory(long categoryId) {
        Category category = getCategoryById(categoryId);
        // destroy relations.
        Category mainCategory = getFirstCategory();
        for (Product product : category.getProducts()) {
            product.setCategory(mainCategory);
            productRepository.save(product);
        }

        category.setProducts(null);
        log.info("This is the category before delete {}", category.getId());
        categoryRepository.deleteById(categoryId);

    }

    public Category getFirstCategory() {
        return categoryRepository.findAll().get(0);
    }


    // get all
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository
                .findAll()
                .stream()
                .map(category -> convertCategoryToCategoryResponse(category))
                .toList();
    }

    public List<CategoryResponse> search(String text) {
        return categoryRepository.findAllByNameContainingOrDetailsContaining(text)
                .stream().map(category -> convertCategoryToCategoryResponse(category))
                .toList();

    }


    // convert category to category response
    private CategoryResponse convertCategoryToCategoryResponse(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .details(category.getDetails())
                .creationDate(category.getCreationDate())
                .numberOfProducts(category.getProducts().size())
                .build();
    }

    // get by id
    public Category getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NoSuchElementException("There Are No Category With Id = " + categoryId)
        );
    }

    public CategoryResponse getCategoryResponseById(long categoryId) {
        Category category = getCategoryById(categoryId);
        return convertCategoryToCategoryResponse(category);
    }

    public List<ProductResponse> getAllProductsForCategory(long categoryId) {
        Category category = getCategoryById(categoryId);

        return category.getProducts().stream().map(ProductResponse::convertProductToProductResponse).toList();
    }


}
