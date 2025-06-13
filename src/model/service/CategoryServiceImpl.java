package model.service;

import model.entity.Category;
import model.repository.CategoryRepositoryImpl;

import java.util.Collections;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();

    @Override
    public void addCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }

        categoryRepository.addCategory(categoryName.trim());
    }

    @Override
    public boolean deleteCategoryById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category ID.");
        }

        Category category = categoryRepository.findById(id);
        if (category != null) {
            return categoryRepository.deleteById(category.getId());
        }

        return false;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategoryByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty.");
        }

        List<Category> categoryList = categoryRepository.searchByName(name.trim());
        return categoryList.isEmpty() ? Collections.emptyList() : categoryList;
    }
}

