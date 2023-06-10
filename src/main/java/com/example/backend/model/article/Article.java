package rs.raf.demo.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Article {

    private Integer id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;
    private Long created_At;
    private Long viewCount;
    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;
    @NotNull(message = "CategoryId is required")
    private Integer categoryId;
    @NotNull(message = "Tags field is required")
    @NotEmpty(message = "Tags field is required")
    private String tags;
    private Long likeCount;
    private Long dislikeCount;

    public Article(){}

    public Article(Integer id, String title, String text, String author, Integer categoryId, String tags) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
        this.categoryId = categoryId;
        this.tags = tags;
        this.created_At = System.currentTimeMillis();
        this.viewCount = 0L;
    }

    public Article(Integer id, String title, String text, String author, Integer categoryId, String tags, Long created_At, Long viewCount, Long likeCount, Long dislikeCount){
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
        this.categoryId = categoryId;
        this.tags = tags;
        this.created_At = created_At;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreated_At() {
        return created_At;
    }

    public void setCreated_At(Long created_At) {
        this.created_At = created_At;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
