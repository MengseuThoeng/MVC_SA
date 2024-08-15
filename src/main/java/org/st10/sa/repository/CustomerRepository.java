package org.st10.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.st10.sa.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByTelephone(String telephone);

}
