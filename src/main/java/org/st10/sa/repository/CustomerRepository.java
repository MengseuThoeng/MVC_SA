package org.st10.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.st10.sa.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
