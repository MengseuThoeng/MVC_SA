package org.st10.sa.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.st10.sa.dto.CustomerCreateRequest;
import org.st10.sa.dto.CustomerEditRequest;
import org.st10.sa.dto.CustomerResponse;
import org.st10.sa.model.Customer;
import org.st10.sa.repository.CustomerRepository;
import org.st10.sa.service.CustomerService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public void createCustomer(CustomerCreateRequest customerCreateRequest) {
        Customer customer = new Customer();

        if (customerCreateRequest == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "CustomerCreateRequest is required"
            );
        }

        if (customerRepository.existsByTelephone(customerCreateRequest.telephone())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Telephone already exists"
            );
        }

        if (customerCreateRequest.score() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Score must be greater than or equal to 0"
            );
        }

        customer.setName(customerCreateRequest.name());
        customer.setScore(customerCreateRequest.score());
        customer.setTelephone(customerCreateRequest.telephone());

        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getTelephone(),
                        customer.getName(),
                        customer.getScore()
                ))
                .toList();
    }

    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));
        return new CustomerResponse(
                customer.getId(),
                customer.getTelephone(),
                customer.getName(),
                customer.getScore()
        );
    }

    @Override
    public void updateCustomer(Long id, CustomerEditRequest customerEditRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

        if (customerEditRequest == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "CustomerEditRequest is required"
            );
        }

        if (customerEditRequest.score() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Score must be greater than or equal to 0"
            );
        }
        customer.setName(customerEditRequest.name());
        customer.setScore(customerEditRequest.score());

        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found"
            );
        }
        customerRepository.deleteById(id);
    }
}
