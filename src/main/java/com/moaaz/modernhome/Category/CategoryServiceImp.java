package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.Product.ProductRepository;
import com.moaaz.modernhome.Product.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {


    private final CategoryRepository categoryRepository;


    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    // add
    @Override
    public CategoryResponse add(CategoryRequest request) {
        Category category
                = categoryMapper.toEntity(request);

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    // update
    @Override
    public CategoryResponse update(CategoryRequest request, Long categoryId) {
        Category existingCategory = getById(categoryId);
        existingCategory.setName(request.getName());
        existingCategory.setDetails(request.getDetails());
        return categoryMapper.toResponse(categoryRepository.save(existingCategory));
    }


    // delete
    @Override
    @Transactional
    public void delete(Long categoryId) {
        Category category = getById(categoryId);
//        // destroy relations.
//        Category mainCategory = getFirstCategory();
//        for (Product product : category.getProducts()) {
//            product.setCategory(mainCategory);
//            productRepository.save(product);
//        }
//
//        category.setProducts(null);
//        log.info("This is the category before delete {}", category.getId());
        categoryRepository.deleteById(categoryId);

    }

    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }


    @Override
    public List<CategoryResponse> search(String text) {
        return categoryRepository.findAllByNameContainingOrDetailsContaining(text)
                .stream().map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NoSuchElementException("There Are No Category With Id = " + categoryId)
        );
    }


    @Override
    public CategoryResponse getResponseById(Long categoryId) {
        Category category = getById(categoryId);
        return categoryMapper.toResponse(category);
    }


}
