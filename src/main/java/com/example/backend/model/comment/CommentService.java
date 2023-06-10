package com.example.backend.model.comment;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment addOne(Comment comment) {return this.commentRepository.addOne(comment); }

    public List<Comment> getAll() { return this.commentRepository.getAll(); }

    public List<Comment> getAll(Integer articleId) { return this.commentRepository.getAll(articleId); }

    public Comment getOne(Integer id) { return this.commentRepository.getOne(id); }

    public void deleteOne(Integer id) { this.commentRepository.deleteOne(id); }
}
