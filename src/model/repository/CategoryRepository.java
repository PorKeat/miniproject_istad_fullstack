package model.repository;

import model.entity.Category;

public interface CategoryRepository {
    Category findById(Integer id);
    void addCategory(String categoryName);
}
