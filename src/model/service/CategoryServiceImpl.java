package model.service;

import model.entity.Category;
import model.repository.CategoryRepositoryImpl;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();

    @Override
    public void addCategory(String categoryName) {
        categoryRepository.addCategory(categoryName);
    }

    @Override
    public boolean deleteCategoryById(Integer id) {
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
        List<Category> categoryList =  categoryRepository.searchByName(name);
        if (!(categoryList.isEmpty())){
            return categoryList;
        }
        return null;
    }
}
