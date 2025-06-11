package model.repository;

import model.entity.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
