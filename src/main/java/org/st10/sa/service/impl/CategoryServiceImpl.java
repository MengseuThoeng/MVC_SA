package org.st10.sa.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.st10.sa.dto.CategoryCreateRequest;
import org.st10.sa.dto.CategoryResponse;
import org.st10.sa.model.Category;
import org.st10.sa.repository.CategoryRepository;
import org.st10.sa.service.CategoryService;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(CategoryCreateRequest categoryCreateRequest) {
        if (categoryCreateRequest == null) {
            log.error("CategoryCreateRequest is null");
            throw new IllegalArgumentException("CategoryCreateRequest is null");
        }

        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            log.error("Category with name {} already exists", categoryCreateRequest.name());
            throw new IllegalArgumentException("Category with name " + categoryCreateRequest.name() + " already exists");
        }

        Category category = new Category();
        category.setName(categoryCreateRequest.name());

        categoryRepository.save(category);
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category with id " + id + " not found"
                )
        );
        return new CategoryResponse(category.getId(), category.getName());
    }

    @Override
    public void updateCategory(Long id, CategoryCreateRequest categoryCreateRequest) {
        if (categoryCreateRequest == null) {
            log.error("CategoryCreateRequest is null");
            throw new IllegalArgumentException("CategoryCreateRequest is null");
        }

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category with id " + id + " not found"
                )
        );

        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            log.error("Category with name {} already exists", categoryCreateRequest.name());
            throw new IllegalArgumentException("Category with name " + categoryCreateRequest.name() + " already exists");
        }

        category.setName(categoryCreateRequest.name());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category with id " + id + " not found"
                )
        );

        // Assuming there is a method in the repository to check if a category is associated with any products
        if (categoryRepository.isCategoryAssociatedWithProducts(id)) {
            log.error("Category with id {} cannot be deleted because it is associated with products", id);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Category with id " + id + " cannot be deleted because it is associated with products"
            );
        }

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList();
    }
}
