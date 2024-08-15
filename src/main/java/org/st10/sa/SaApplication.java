package org.st10.sa;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.st10.sa.model.Category;
import org.st10.sa.model.Customer;
import org.st10.sa.model.Product;
import org.st10.sa.repository.CategoryRepository;
import org.st10.sa.repository.CustomerRepository;
import org.st10.sa.repository.ProductRepository;

@SpringBootApplication
public class SaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaApplication.class, args);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    void addCategory() {
        if (categoryRepository.count() < 0) {
            Category category = new Category();
            category.setName("Electronics");
            categoryRepository.save(category);
        }

        if (productRepository.count() < 0) {
            Product product = new Product();
            product.setName("Laptop");
            product.setPrice(1000.0);
            product.setQty(10);
            product.setCategory(categoryRepository.findByName("Electronics").orElseThrow(null));
            productRepository.save(product);
        }

        if (customerRepository.count() < 0) {
            Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setScore(100.0);
            customer.setTelephone("1234567890");
            customerRepository.save(customer);
        }
    }

}

