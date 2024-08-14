package org.st10.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.st10.sa.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
