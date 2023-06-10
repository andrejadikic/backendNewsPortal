package rs.raf.demo.dto.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ArticleUpdateDto {

    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String title;
    @NotNull(message = "Text is required")
    @NotEmpty(message = "Text is required")
    private String text;
    @NotNull(message = "Author is required")
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull(message = "Category is required")
    private Integer categoryId;

    public ArticleUpdateDto(){

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
}
