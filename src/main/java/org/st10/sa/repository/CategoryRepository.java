package org.st10.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.st10.sa.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Product p WHERE p.category.id = :categoryId")
    Boolean isCategoryAssociatedWithProducts(@Param("categoryId") Long categoryId);

    Optional<Category> findByName(String name);
}
