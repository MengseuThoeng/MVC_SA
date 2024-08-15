package org.st10.sa.service;

import org.st10.sa.dto.CustomerCreateRequest;
import org.st10.sa.dto.CustomerEditRequest;
import org.st10.sa.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    void createCustomer(CustomerCreateRequest customerCreateRequest);

    List<CustomerResponse> getCustomers();

    CustomerResponse getCustomer(Long id);

    void updateCustomer(Long id, CustomerEditRequest customerEditRequest);

    void deleteCustomer(Long id);

}
