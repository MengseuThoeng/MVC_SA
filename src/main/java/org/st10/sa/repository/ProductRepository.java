package org.st10.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.st10.sa.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
