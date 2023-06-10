package com.example.backend.model.article;

import com.example.backend.application.ReqException;
import com.example.backend.application.dto.ArticleUpdateDto;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;

    public List<Article> addOne(Article article) {
        this.articleRepository.addOne(article);
        return this.articleRepository.getAll();
    }

    public List<Article> getAll(){
        List<Article> articles = this.articleRepository.getAll();
        sortByDate(articles);
        return articles;
        //return articles.stream().limit(10).collect(Collectors.toList());
    }

    public List<Article> getbyPage(Integer page){
        List<Article> articles = this.articleRepository.getAll();
        sortByDate(articles);
        return articles.stream().skip(10*(page-1)).limit(10).collect(Collectors.toList());
    }

    public List<Article> getByName(String text, Integer categodyId){
        List<Article> articles = this.articleRepository.getByName(text);
        return categodyId == null
                ? articles
                : articles.stream().filter(article -> article.getCategoryId().equals(categodyId)).collect(Collectors.toList());
    }

    public Article getOne(Integer id) { return this.articleRepository.get(id); }

    public List<Article> deleteOne(Integer id) {
        this.articleRepository.delete(id);
        return this.articleRepository.getAll();
    }

    public List<Article> getAll(Integer categoryId) {
        List<Article> articles = this.articleRepository.getAll(categoryId);
        sortByDate(articles);
        return articles;
    }

    public List<Article> getByTag(String tag) {
        List<Article> articles = this.articleRepository.getAll();
        return articles.stream().filter(article -> {
            System.out.println(article.getTags());
            return article.getTags().contains(tag);}).collect(Collectors.toList()).stream().limit(10).collect(Collectors.toList());
    }

    public List<Article> getByTags(List<String> tags, Integer articleId){
        List<Article> articles = this.articleRepository.getAll();
        List<Article> results = new ArrayList<>();
        tags.forEach(tag -> {
            results.addAll(articles.stream().filter(article -> article.getTags().contains(tag)).collect(Collectors.toList()));
        });
        results.removeIf(article -> article.getId().equals(articleId));
        return results.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Article::getId))),
                        ArrayList::new));
    }

    public List<Article> mostRead(){
        List<Article> articles = this.articleRepository.getAll();
        LocalDateTime monthAgo = LocalDate.now().minusMonths(1).atTime(0, 0);
        long timestamp = Timestamp.valueOf(monthAgo).getTime();
        articles.sort(Comparator.comparing(Article::getViewCount).reversed());
        articles.removeIf(article -> article.getCreated_At() <= timestamp);
        return articles.stream().limit(10).collect(Collectors.toList());
    }

    public List<Article> mostRead(Integer page){
        List<Article> articles = this.articleRepository.getAll();
        LocalDateTime monthAgo = LocalDate.now().minusMonths(1).atTime(0, 0);
        long timestamp = Timestamp.valueOf(monthAgo).getTime();
        articles.sort(Comparator.comparing(Article::getViewCount).reversed());
        articles.removeIf(article -> article.getCreated_At() <= timestamp);
        return articles.stream().skip(10*(page-1)).limit(10).collect(Collectors.toList());
    }

    public Article increaseViewCount(Integer articleId) throws ReqException {
        Article article = this.articleRepository.get(articleId);
        if(article == null) throw new ReqException("Invalid article", 400);
        this.articleRepository.increaseViews(articleId, article.getViewCount() + 1);
        return this.articleRepository.get(articleId);
    }

    public List<Article> updateOne(Integer articleId, ArticleUpdateDto data){
        this.articleRepository.update(articleId, data);
        return this.articleRepository.getAll();
    }

    private void sortByDate(List<Article> articles){
        articles.sort(Comparator.comparing(Article::getCreated_At).reversed());
    }

}
