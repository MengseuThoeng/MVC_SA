package org.st10.sa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.st10.sa.dto.CategoryResponse;
import org.st10.sa.dto.ProductCreateRequest;
import org.st10.sa.dto.ProductResponse;
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
        List<CategoryResponse> categories = categoryService.getCategories();
        model.addAttribute("product", productResponse);
        model.addAttribute("categories", categories);
        return "edit-product";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productCreateRequest", new ProductCreateRequest("", 0.0, 0, null, ""));
        model.addAttribute("categories", categoryService.getCategories());
        return "create-product";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductCreateRequest productCreateRequest,
                                @RequestParam("image") MultipartFile image) {
        productCreateRequest = new ProductCreateRequest(
                productCreateRequest.name(),
                productCreateRequest.price(),
                productCreateRequest.qty(),
                image,
                productCreateRequest.categoryName()
        );
        productService.createProduct(productCreateRequest);
        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute ProductCreateRequest productCreateRequest,
                              @RequestParam("image") MultipartFile image) {
        productCreateRequest = new ProductCreateRequest(
                productCreateRequest.name(),
                productCreateRequest.price(),
                productCreateRequest.qty(),
                image,
                productCreateRequest.categoryName()
        );
        productService.updateProduct(id, productCreateRequest);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
