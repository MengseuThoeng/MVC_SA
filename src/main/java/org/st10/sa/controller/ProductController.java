package org.st10.sa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.st10.sa.dto.CategoryResponse;
import org.st10.sa.dto.ProductCreateRequest;
import org.st10.sa.dto.ProductResponse;
import org.st10.sa.model.Category;
import org.st10.sa.model.Product;
import org.st10.sa.service.CategoryService;
import org.st10.sa.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getProducts(Model model) {
        List<ProductResponse> products = productService.getProducts();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        ProductResponse productResponse = productService.getProduct(id);
        List<CategoryResponse> categories = categoryService.getCategories(); // get all categories
        model.addAttribute("product", productResponse); // ensure attribute name matches
        model.addAttribute("categories", categories);
        return "edit-product";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productCreateRequest", new ProductCreateRequest("", 0.0, 0, ""));
        model.addAttribute("categories", categoryService.getCategories());
        return "create-product";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductCreateRequest productCreateRequest) {
        productService.createProduct(productCreateRequest);
        return "redirect:/products";
    }

//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        ProductResponse product = productService.getProduct(id);
//        model.addAttribute("productCreateRequest", new ProductCreateRequest(
//                product.name(), product.price(), product.qty(), product.categoryName()));
//        model.addAttribute("categories", categoryService.getCategories());
//        return "edit-product";
//    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute ProductCreateRequest productCreateRequest) {
        productService.updateProduct(id, productCreateRequest);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
