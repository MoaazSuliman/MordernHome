package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.generic.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService extends CrudService<CategoryRequest, Category, CategoryResponse, Long> {

    List<CategoryResponse> search(String text);
}
