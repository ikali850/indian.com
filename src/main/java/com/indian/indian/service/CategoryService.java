package com.indian.indian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indian.indian.entity.Category;
import com.indian.indian.repository.CategoryRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    // Create a new category
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    // Read all categories
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    // Read all categories
    public List<Category> getAllById(List list) {
        return categoryRepo.findAllById(list);
    }

    // Read a category by ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepo.findById(id);
    }

    // category exist by name
    public boolean categoryExist(String name) {
        return this.categoryRepo.existsByName(name);
    }

    // category exist by Id
    public boolean categoryExist(Long id) {
        return this.categoryRepo.existsById(id);
    }

    // Update a category
    public Category updateCategory(Long id, Category updatedCategory) {
        if (categoryRepo.existsById(id)) {
            updatedCategory.setId(id); // Set the ID for the entity being updated
            return categoryRepo.save(updatedCategory);
        }
        throw new EntityNotFoundException("Category not found with id: " + id);
    }

    // Delete a category by ID
    public void deleteCategory(Long id) {
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
    }

}