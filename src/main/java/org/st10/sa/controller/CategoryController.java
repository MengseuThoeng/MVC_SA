package org.st10.sa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.st10.sa.dto.CategoryCreateRequest;
import org.st10.sa.dto.CategoryResponse;
import org.st10.sa.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryResponse> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "category";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryCreateRequest", new CategoryCreateRequest(""));
        return "create-category";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryCreateRequest categoryCreateRequest) {
        categoryService.createCategory(categoryCreateRequest);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CategoryResponse category = categoryService.getCategory(id);
        model.addAttribute("categoryCreateRequest", new CategoryCreateRequest(category.name()));
        model.addAttribute("categoryId", id);
        return "edit-category";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryCreateRequest categoryCreateRequest) {
        categoryService.updateCategory(id, categoryCreateRequest);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
