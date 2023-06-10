package rs.raf.demo.article;

import rs.raf.demo.article.repository.ArticleRepository;
import rs.raf.demo.dto.ArticleInfo;
import rs.raf.demo.dto.update.ArticleUpdateDto;
import rs.raf.demo.like.LikeService;
import rs.raf.demo.enums.Constants;
import rs.raf.demo.enums.ReqException;
import rs.raf.demo.models.Article;
import rs.raf.demo.models.Like;
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
    @Inject
    private LikeService likeService;

    public List<Article> addOne(Article article) {
        this.articleRepository.addOne(article);
        return this.articleRepository.getAll();
    }

    public List<Article> getAll(){
        List<Article> articles = this.articleRepository.getAll();
        sortByDate(articles);
        return articles;
    }

    public List<Article> getByName(String text, Integer categodyId){
        List<Article> articles = this.articleRepository.getByName(text);
        return categodyId == null
                ? articles
                : articles.stream().filter(article -> article.getCategoryId().equals(categodyId)).collect(Collectors.toList());
    }

    public Article getOne(Integer id) { return this.articleRepository.getOne(id); }

    public List<Article> deleteOne(Integer id) {
        this.articleRepository.deleteOne(id);
        return this.articleRepository.getAll();
    }

    public Article likeOne(Like like, Integer userId) throws ReqException {
        Article article = this.articleRepository.getOne(like.getEntityId());
        if(article == null) throw new ReqException("Invalid article", 400);
        Like existingLike = this.likeService.getOne(article.getId(), userId, like.getEntityType());
        if(existingLike == null) {
            this.likeService.addOne(like);
            Long likeCount = like.getLikeType().equals(Constants.LikeType.LIKE) ? article.getLikeCount() : article.getDislikeCount();
            this.articleRepository.updateOne(likeCount + 1, like.getLikeType(), article.getId());
        }else{
            switch (existingLike.getLikeType()){
                case Constants.LikeType.LIKE:
                    if(like.getLikeType().equals(Constants.LikeType.DISLIKE)){
                        this.likeService.changeType(existingLike.getId(), Constants.LikeType.DISLIKE);
                        this.articleRepository.updateOne(article.getLikeCount() - 1, Constants.LikeType.LIKE, article.getId());
                        this.articleRepository.updateOne(article.getDislikeCount() + 1, Constants.LikeType.DISLIKE, article.getId());
                        break;
                    }
                    if(like.getLikeType().equals(Constants.LikeType.LIKE)){
                        throw new ReqException("You have already liked this article", 400);
                    }
                case Constants.LikeType.DISLIKE:
                    if(like.getLikeType().equals(Constants.LikeType.LIKE)){
                        this.likeService.changeType(existingLike.getId(), Constants.LikeType.LIKE);
                        this.articleRepository.updateOne(article.getLikeCount() + 1, Constants.LikeType.LIKE, article.getId());
                        this.articleRepository.updateOne(article.getDislikeCount() - 1, Constants.LikeType.DISLIKE, article.getId());
                        break;
                    }
                    if(like.getLikeType().equals(Constants.LikeType.DISLIKE)){
                        throw new ReqException("You have already disliked this article", 400);
                    }
            }
        }
        return this.articleRepository.getOne(article.getId());
    }

    public List<Article> getAll(Integer categoryId) {
        List<Article> articles = this.articleRepository.getAll(categoryId);
        sortByDate(articles);
        return articles;
    }

    public List<Article> getByTag(String tag) {
        List<Article> articles = this.articleRepository.getAll();
        return articles.stream().limit(3).filter(article -> article.getTags().contains(tag)).collect(Collectors.toList());
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

    public List<Article> mostReacted(){
        List<Article> articles = this.articleRepository.getAll();
        List<Article> mostReactedArticles = new ArrayList<>();
        List<ArticleInfo> meta = new ArrayList<>();
        for(int index =0; index < articles.size(); index++){
            Article article = articles.get(index);
            meta.add(new ArticleInfo(index, article.getLikeCount() + article.getDislikeCount()));
        }
        System.out.println(meta.size());
        meta.sort(Comparator.comparing(ArticleInfo::getLikeSum).reversed());
        for(int i = 0; i < 5; i ++)
            mostReactedArticles.add(articles.get(meta.get(i).getIndex()));
        return mostReactedArticles;
    }

    public List<Article> mostRead(){
        List<Article> articles = this.articleRepository.getAll();
        LocalDateTime monthAgo = LocalDate.now().minusMonths(1).atTime(0, 0);
        long timestamp = Timestamp.valueOf(monthAgo).getTime();
        articles.sort(Comparator.comparing(Article::getViewCount).reversed());
        articles.removeIf(article -> article.getCreated_At() <= timestamp);
        return articles.stream().limit(10).collect(Collectors.toList());
    }

    public Article increaseViewCount(Integer articleId) throws ReqException {
        Article article = this.articleRepository.getOne(articleId);
        if(article == null) throw new ReqException("Invalid article", 400);
        this.articleRepository.increaseViews(articleId, article.getViewCount() + 1);
        return this.articleRepository.getOne(articleId);
    }

    public List<Article> updateOne(Integer articleId, ArticleUpdateDto data){
        this.articleRepository.updateOne(articleId, data);
        return this.articleRepository.getAll();
    }

    private void sortByDate(List<Article> articles){
        articles.sort(Comparator.comparing(Article::getCreated_At).reversed());
    }

}
