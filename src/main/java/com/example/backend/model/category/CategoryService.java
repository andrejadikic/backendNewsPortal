package rs.raf.demo.category;

import rs.raf.demo.article.ArticleService;
import rs.raf.demo.category.repository.CategoryRepository;
import rs.raf.demo.enums.ReqException;
import rs.raf.demo.models.Article;
import rs.raf.demo.models.Category;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;
    @Inject
    private ArticleService articleService;

    public Category addOne(Category category) { return this.categoryRepository.addOne(category); }

    public List<Category> getAll() { return this.categoryRepository.getAll(); }

    public Category getOne(Integer id) { return this.categoryRepository.getOne(id); }

    public void deleteOne(Integer id) throws ReqException {
        List<Article> articles = this.articleService.getAll(id);
        if(!articles.isEmpty()) throw new ReqException("Articles with the selected category exist, delete them first", 403);
        this.categoryRepository.deleteOne(id);
    }

    public Category getOne(String name) { return this.categoryRepository.getOne(name); }

    public List<Category> updateOne(Integer id, Category category) throws ReqException {
        Category existingCategory = this.getOne(category.getName());
        if(existingCategory != null) throw new ReqException("Category with the same name already exists", 400);
        this.categoryRepository.updateOne(id, category);
        return this.categoryRepository.getAll();
    }
}
