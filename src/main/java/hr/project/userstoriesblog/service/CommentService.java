package hr.project.userstoriesblog.service;

import hr.project.userstoriesblog.model.Blog;
import hr.project.userstoriesblog.model.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();
    void saveComment(Comment comment);
    Comment getCommentById(long id);
    void deleteCommentById(long id);

}
