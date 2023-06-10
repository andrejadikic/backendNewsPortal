package rs.raf.demo.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {

    private Integer id;
    private String author;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;
    @NotNull(message = "Article id is required")
    private Integer articleId;
    private Long likeCount;
    private Long dislikeCount;
    private Long created_at;

    public Comment(){}

    public Comment(Integer id, String author, String text, Integer articleId) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.articleId = articleId;
        this.created_at = System.currentTimeMillis();
    }

    public Comment(Integer id, String author, String text, Integer articleId, Long created_at, Long likeCount, Long dislikeCount) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.articleId = articleId;
        this.created_at = created_at;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }
}
