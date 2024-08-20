package org.st10.sa.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.st10.sa.dto.ProductCreateRequest;
import org.st10.sa.dto.ProductResponse;
import org.st10.sa.model.Category;
import org.st10.sa.model.Product;
import org.st10.sa.repository.CategoryRepository;
import org.st10.sa.repository.ProductRepository;
import org.st10.sa.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Value("${media.server-path}")
    private String SERVER_IMAGE_PATH;

    @Value("${media.base-uri}")
    private String SERVER_IMAGE_URL;

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

        Category category = categoryRepository.findByName(productCreateRequest.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = new Product();
        product.setName(productCreateRequest.name());
        product.setPrice(productCreateRequest.price());
        product.setQty(productCreateRequest.qty());
        product.setCategory(category);

        MultipartFile image = productCreateRequest.image();

        // Handle the image upload
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImageFile(image); // Save the image and get the path
            product.setImage(imagePath);
        }

        productRepository.save(product);
    }

    private String saveImageFile(MultipartFile imageFile) {
        try {
            String fileName = imageFile.getOriginalFilename();
            String uploadDir = SERVER_IMAGE_PATH;// Specify your upload directory
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            assert fileName != null;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return SERVER_IMAGE_URL + "IMAGE/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
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
                product.getImage(),
                product.getCategory().getName()
        );
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

        MultipartFile image = productCreateRequest.image();

        // Handle the image upload
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImageFile(image); // Save the image and get the path
            product.setImage(imagePath);
        }

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
                        product.getImage(),
                        product.getCategory().getName()
                ))
                .toList();
    }
}
