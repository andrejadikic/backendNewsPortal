package rs.raf.demo.comment;

import rs.raf.demo.comment.repository.CommentRepository;
import rs.raf.demo.enums.Constants;
import rs.raf.demo.enums.ReqException;
import rs.raf.demo.like.LikeService;
import rs.raf.demo.models.Comment;
import rs.raf.demo.models.Like;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;
    @Inject
    private LikeService likeService;

    public Comment addOne(Comment comment) {return this.commentRepository.addOne(comment); }

    public List<Comment> getAll() { return this.commentRepository.getAll(); }

    public List<Comment> getAll(Integer articleId) { return this.commentRepository.getAll(articleId); }

    public Comment getOne(Integer id) { return this.commentRepository.getOne(id); }

    public void deleteOne(Integer id) { this.commentRepository.deleteOne(id); }

    public Comment likeOne(Like like, Integer userId) throws ReqException {
       Comment comment = this.commentRepository.getOne(like.getEntityId());
       if(comment == null) throw new ReqException("Invalid comment", 400);
       Like existingLike = this.likeService.getOne(comment.getId(), userId, like.getEntityType());
       if(existingLike == null){
           this.likeService.addOne(like);
           Long likeCount = like.getLikeType().equals(Constants.LikeType.LIKE) ? comment.getLikeCount() : comment.getDislikeCount();
           this.commentRepository.updateOne(likeCount + 1, like.getLikeType(), comment.getId());
       }else {
           switch (existingLike.getLikeType()){
               case Constants.LikeType.LIKE:
                   if(like.getLikeType().equals(Constants.LikeType.DISLIKE)){
                       this.likeService.changeType(existingLike.getId(), Constants.LikeType.DISLIKE);
                       this.commentRepository.updateOne(comment.getLikeCount() - 1, Constants.LikeType.LIKE, comment.getId());
                       this.commentRepository.updateOne(comment.getDislikeCount() + 1, Constants.LikeType.DISLIKE, comment.getId());
                       break;
                   }
                   if(like.getLikeType().equals(Constants.LikeType.LIKE)){
                       throw new ReqException("You have already liked this comment", 400);
                   }
               case Constants.LikeType.DISLIKE:
                   if(like.getLikeType().equals(Constants.LikeType.LIKE)){
                       this.likeService.changeType(existingLike.getId(), Constants.LikeType.LIKE);
                       this.commentRepository.updateOne(comment.getLikeCount() + 1, Constants.LikeType.LIKE, comment.getId());
                       this.commentRepository.updateOne(comment.getDislikeCount() - 1, Constants.LikeType.DISLIKE, comment.getId());
                       break;
                   }
                   if(like.getLikeType().equals(Constants.LikeType.DISLIKE)){
                       throw new ReqException("You have already disliked this comment", 400);
                   }
           }
       }
       return this.commentRepository.getOne(comment.getId());
    }
}
