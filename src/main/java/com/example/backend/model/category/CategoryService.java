package com.example.backend.model.category;


import com.example.backend.application.*;
import com.example.backend.model.article.Article;
import com.example.backend.model.article.ArticleService;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;
    @Inject
    private ArticleService articleService;

    public Category addOne(Category category) { return this.categoryRepository.add(category); }

    public List<Category> getAll() { return this.categoryRepository.getAll(); }

    public Category getOne(Integer id) { return this.categoryRepository.get(id); }

    public void deleteOne(Integer id) throws ReqException {
        List<Article> articles = this.articleService.getAll(id);
        if(!articles.isEmpty()) throw new ReqException("Articles with the selected category exist, delete them first", 403);
        this.categoryRepository.delete(id);
    }

    public Category getOne(String name) { return this.categoryRepository.get(name); }

    public List<Category> updateOne(Integer id, Category category) throws ReqException {
        Category existingCategory = this.getOne(category.getName());
        if(existingCategory != null && !Objects.equals(id, existingCategory.getId())) throw new ReqException("Category with the same name already exists", 400);
        this.categoryRepository.update(id, category);
        return this.categoryRepository.getAll();
    }
}
