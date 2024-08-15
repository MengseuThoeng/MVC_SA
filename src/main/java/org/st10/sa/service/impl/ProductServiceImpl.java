package org.st10.sa.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.st10.sa.dto.ProductCreateRequest;
import org.st10.sa.dto.ProductResponse;
import org.st10.sa.model.Category;
import org.st10.sa.model.Product;
import org.st10.sa.repository.CategoryRepository;
import org.st10.sa.repository.ProductRepository;
import org.st10.sa.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void createProduct(ProductCreateRequest productCreateRequest) {
        if (productCreateRequest == null) {
            log.error("ProductCreateRequest is null");
            throw new IllegalArgumentException("ProductCreateRequest is null");
        }

        if (productCreateRequest.qty() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (productCreateRequest.price() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        Category category = categoryRepository.findByName(productCreateRequest.categoryName()).orElseThrow(
                () -> new IllegalArgumentException(
                        "Category with name " + productCreateRequest.categoryName() + " not found"
                )
        );

        Product product = new Product();
        product.setName(productCreateRequest.name());
        product.setPrice(productCreateRequest.price());
        product.setQty(productCreateRequest.qty());
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public ProductResponse getProduct(Long id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with id " + id + " not found")
        );

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQty(),
                product.getCategory().getName());
    }

    @Override
    public void updateProduct(Long id, ProductCreateRequest productCreateRequest) {
        if (productCreateRequest == null) {
            log.error("ProductCreateRequest is null");
            throw new IllegalArgumentException("ProductCreateRequest is null");
        }

        if (productCreateRequest.qty() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (productCreateRequest.price() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        Category category = categoryRepository.findByName(productCreateRequest.categoryName()).orElseThrow(
                () -> new IllegalArgumentException(
                        "Category with name " + productCreateRequest.categoryName() + " not found"
                )
        );

        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with id " + id + " not found")
        );

        product.setName(productCreateRequest.name());
        product.setPrice(productCreateRequest.price());
        product.setQty(productCreateRequest.qty());
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with id " + id + " not found")
        );

        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQty(),
                        product.getCategory().getName()
                ))
                .toList();
    }
}
