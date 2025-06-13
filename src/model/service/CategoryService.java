package model.service;

import model.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(String categoryName);
    boolean deleteCategoryById(Integer id);
    List<Category> getAllCategories();
    List<Category> searchCategoryByName(String name);
}
