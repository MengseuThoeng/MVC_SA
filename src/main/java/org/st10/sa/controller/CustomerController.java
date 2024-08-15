package org.st10.sa.controller;

import lombok.RequiredArgsConstructor;
import org.st10.sa.dto.CustomerCreateRequest;
import org.st10.sa.dto.CustomerEditRequest;
import org.st10.sa.dto.CustomerResponse;
import org.st10.sa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model) {
        List<CustomerResponse> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "customer";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        // Use placeholder values or empty strings for the CustomerCreateRequest constructor
        model.addAttribute("customerCreateRequest", new CustomerCreateRequest("", "", 0.0));
        return "create-customer";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute CustomerCreateRequest customerCreateRequest) {
        customerService.createCustomer(customerCreateRequest);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CustomerResponse customer = customerService.getCustomer(id);
        model.addAttribute("customerEditRequest", new CustomerEditRequest(customer.name(), customer.score()));
        model.addAttribute("customerId", id);
        return "edit-customer";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute CustomerEditRequest customerEditRequest) {
        customerService.updateCustomer(id, customerEditRequest);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
