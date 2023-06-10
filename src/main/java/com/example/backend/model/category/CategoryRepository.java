package com.example.backend.model.category;


import java.util.List;

public interface CategoryRepository {
    List<Category> getAll();
    Category add(Category category);
    Category get(Integer id);
    void delete(Integer id);
    Category get(String name);
    void update(Integer id, Category category);
}
