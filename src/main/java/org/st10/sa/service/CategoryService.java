package org.st10.sa.service;

import org.st10.sa.dto.CategoryCreateRequest;
import org.st10.sa.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

        void createCategory(CategoryCreateRequest categoryCreateRequest);

        CategoryResponse getCategory(Long id);

        void updateCategory(Long id, CategoryCreateRequest categoryCreateRequest);

        void deleteCategory(Long id);

        List<CategoryResponse> getCategories();
}
