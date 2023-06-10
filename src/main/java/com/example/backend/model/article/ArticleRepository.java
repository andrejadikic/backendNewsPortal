package com.example.backend.model.article.repository;


import com.example.backend.application.dto.ArticleUpdateDto;
import com.example.backend.model.article.Article;

import java.util.List;

public interface ArticleRepository {
    Article addOne(Article article);
    List<Article> getAll();
    List<Article> getAll(Integer categoryId);
    List<Article> getByName(String text);
    void increaseViews(Integer articleId, long newCount);
    Article getOne(Integer id);
    void deleteOne(Integer id);
    void updateOne(Integer id, ArticleUpdateDto data);
}
