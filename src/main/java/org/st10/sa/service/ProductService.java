package org.st10.sa.service;

import org.st10.sa.dto.CategoryCreateRequest;
import org.st10.sa.dto.ProductCreateRequest;
import org.st10.sa.dto.ProductResponse;
import org.st10.sa.model.Product;

import java.util.List;

public interface ProductService {

    void createProduct(ProductCreateRequest productCreateRequest);

    ProductResponse getProduct(Long id);

    void updateProduct(Long id, ProductCreateRequest productCreateRequest);

    void deleteProduct(Long id);

    List<ProductResponse> getProducts();

    Product findById(Long id);
}
