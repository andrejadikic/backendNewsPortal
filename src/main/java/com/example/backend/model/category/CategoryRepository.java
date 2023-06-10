package com.example.backend.model.category.repository;

import rs.raf.demo.models.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAll();
    Category addOne(Category category);
    Category getOne(Integer id);
    void deleteOne(Integer id);
    Category getOne(String name);
    void updateOne(Integer id, Category category);
}
